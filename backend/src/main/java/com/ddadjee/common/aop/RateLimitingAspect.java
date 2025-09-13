package com.ddadjee.common.aop;

import com.ddadjee.common.annotation.RateLimiting;
import com.ddadjee.common.error.BusinessException;
import com.ddadjee.common.error.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * {@link RateLimiting} 처리를 위한 AOP.
 * <p>
 * - Redis 카운터(INCR) + TTL 고정 윈도우 방식.
 * - 키는 메서드와 SpEL 평가 결과를 조합하여 구성합니다.
 * - Redis 연결 실패 시에는 제한을 우회(가용성 우선)합니다.
 */
@Aspect
@Component
public class RateLimitingAspect {

    private final StringRedisTemplate redis;
    private final ExpressionParser parser = new SpelExpressionParser();

    public RateLimitingAspect(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @Around("@annotation(rateLimiting)")
    public Object around(ProceedingJoinPoint pjp, RateLimiting rateLimiting) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String baseKey = method.getDeclaringClass().getName() + ":" + method.getName();
        String dynamicPart = evaluateKey(pjp, rateLimiting.key());
        String key = "rl:" + baseKey + (dynamicPart.isEmpty() ? "" : (":" + dynamicPart));

        int permits = rateLimiting.permits();
        int windowSeconds = rateLimiting.windowSeconds();

        try {
            Long count = redis.opsForValue().increment(key);
            if (count != null && count == 1L) {
                redis.expire(key, windowSeconds, TimeUnit.SECONDS);
            }
            if (count != null && count > permits) {
                throw new BusinessException(ErrorCode.TOO_MANY_REQUESTS, rateLimiting.message());
            }
        } catch (Exception e) {
            // Redis 장애 시 제한 우회 (로그만 남기고 진행)
        }

        return pjp.proceed();
    }

    private String evaluateKey(ProceedingJoinPoint pjp, String spel) {
        if (spel == null || spel.isBlank()) return "";
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Method method = sig.getMethod();
        String[] paramNames = sig.getParameterNames();
        Object[] args = pjp.getArgs();
        EvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++) {
            String name = (paramNames != null && i < paramNames.length && paramNames[i] != null)
                    ? paramNames[i]
                    : ("p" + i);
            ctx.setVariable(name, args[i]);
            ctx.setVariable("p" + i, args[i]);
            ctx.setVariable("a" + i, args[i]);
        }
        Expression exp = parser.parseExpression(spel.startsWith("#") ? spel : ("#" + spel));
        Object v = exp.getValue(ctx);
        return v == null ? "" : v.toString();
    }
}

