package com.ddadjee.iam.adapters.out.events.spring;

import com.ddadjee.iam.application.port.out.DomainEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * DomainEventPublisherPort 구현(Spring ApplicationEvent 사용).
 * 기술명은 패키지로 표현하고, 클래스명은 역할 중심으로 명명합니다.
 */
@Component
@RequiredArgsConstructor
public class ApplicationEventDomainEventPublisher implements DomainEventPublisherPort {
    private final ApplicationEventPublisher publisher;

    @Override
    public void publishAll(Iterable<?> events) {
        events.forEach(publisher::publishEvent);
    }
}
