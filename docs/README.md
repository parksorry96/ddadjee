# Git Branch & PR Convention

 ## 브랜치 전략
 - 기본 브랜치: `main` (보호 대상). 모든 변경은 PR로 머지.
 - 방식: Trunk‑Based + 짧은 기능 브랜치. 필요 시 핫픽스만 직접 배포 브랜치에서 분기.

 ## 브랜치 네이밍
 - 형식: `<type>/<scope>-<short-slug>`
 - type: `feat` | `fix` | `refactor` | `chore` | `docs` | `test` | `hotfix`
 - scope: 모듈 또는 영역
   - 백엔드: `iam`, `common`, `catalog`, `schedule`, `wallet`, `applications`, `resume`, `recommendation`, `analytics`, `community`, `integration`
   - 프런트엔드: `frontend`, `ui`, `routing` 등
 - 예시
   - `feat/iam-signup-usecase`
   - `fix/catalog-dup-normalization`
   - `chore/frontend-build-ci`
   - `hotfix/schedule-npe`
 - 이슈 번호가 있다면 선행: `DDJ-123/feat/iam-signup-usecase`

 ## 작업 흐름
 1) 최신화: `git checkout main && git pull`
 2) 브랜치 생성: `git checkout -b feat/iam-signup-usecase`
 3) TDD 진행(도메인→유스케이스→어댑터). 작은 커밋 OK, PR 시 스쿼시 권장.
 4) 리베이스 정리: `git fetch && git rebase origin/main`
 5) PR 생성 후 CI 통과 확인, 리뷰 셀프체크.
 6) 머지: `Squash & merge`, 브랜치 삭제.

 ## PR 규칙
 - 제목: Conventional Commits. 예) `feat(iam): 회원가입 유스케이스 추가`
 - 본문: 배경/변경점/테스트/영향/스크린샷(UI). 관련 이슈 링크.
 - 체크리스트: 빌드 통과(백엔드 `./gradlew test`, 프런트 `npm run lint`), 브레이킹 변경 명시.

 ## 태그/릴리스(선택)
 - 태그: `vMAJOR.MINOR.PATCH` (예: `v0.1.0`).
- 변경 로그는 PR 타이틀 기준 자동 생성 가능(추후 설정).

## Custom Annotations Catalog
- 목적: 반복되는 보일러플레이트 제거, 정책 일관성 유지, AOP/검증을 선언적으로 적용.
- 위치: `backend/src/main/java/com/ddadjee/common/annotation`

- UseCase / ReadOnlyUseCase
  - `@UseCase`(쓰기 트랜잭션), `@ReadOnlyUseCase`(읽기 전용). 애플리케이션 서비스 클래스에 부착.
  - 예: `@UseCase class UserRegistrationService { ... }`

- Loggable / Sensitive
  - `@Loggable(args = false, result = false)`로 START/END/소요시간 로깅(AOP 필요).
  - `@Sensitive(maskWith="****", exposeTail=4)`로 민감정보 마스킹 힌트.

- CurrentUser (권장)
  - `@AuthenticationPrincipal`를 감싼 파라미터 애너테이션. 컨트롤러에서 인증 사용자 주입.
  - 예: `public ApiResponse<?> me(@CurrentUser UserPrincipal me)`

- Bean Validation 커스텀 제약
  - `@ValidPassword`(강도 규칙), `@PasswordMatches`(교차필드), `@UniqueEmail`(고유성 검사).
  - 구현: `@Constraint(validatedBy=...)` + Validator로 리포지토리/규칙 주입.

- RateLimiting / Idempotent (선택)
  - `@RateLimiting(key="#userId", permits=5, per="1m")` 버킷4j 기반 레이트리미팅.
  - `@Idempotent(key="#requestId", ttl="2m")` 중복요청 방지(분산 락/Redis).

- SchedulerLock (배치)
  - `@SchedulerLock(name="catalogSync", lockAtMostFor="5m")` — ShedLock 기반 분산 스케줄 보호.

- FeatureGate (토글)
  - `@FeatureGate("RECOMMENDATION_V2")` 활성 여부 검사 후 기능 실행(Togglz 등과 연계).

- Multi‑Tenancy (옵션)
  - `@CurrentTenant`/`@TenantId`로 테넌트 식별자 주입(ArgumentResolver/AOP 필요).

도입 단계(제안)
1) Core: UseCase, ReadOnlyUseCase, Loggable, Sensitive, CurrentUser
2) Validation: ValidPassword, PasswordMatches, UniqueEmail
3) Reliability: RateLimiting, Idempotent
4) Ops: SchedulerLock, FeatureGate, (필요 시) Multi‑Tenancy
