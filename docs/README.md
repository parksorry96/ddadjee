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
