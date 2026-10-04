# Work Log

이 문서는 WorkFlow 프로젝트의 실제 작업 진행 상황을 기록합니다.

README.md는 프로젝트 개요, 업무 흐름, ERD, 기술 스택을 설명하는 문서로 사용하고, WORK_LOG.md는 작업 단위별 완료 내용, 검증 결과, 다음 작업을 정리하는 용도로 사용합니다.

## 작업 기록 규칙

- 작업이 끝나면 완료된 내용을 간단히 기록합니다.
- 테스트나 빌드 검증 결과를 함께 기록합니다.
- 다음 작업 후보와 작업 범위를 적어 이어서 개발하기 쉽게 관리합니다.
- 코드 작성 시 클래스, 메서드, 주요 필드, Enum 값에는 용도 중심의 주석을 작성합니다.

## 완료된 작업

### 작업 0 - GitHub 저장소 연결

완료일: 2026-10-04

완료 내용:

- 로컬 Git 저장소와 GitHub 원격 저장소를 연결했습니다.
- 원격 저장소 URL을 `https://github.com/shekdns/workflow.git`로 설정했습니다.
- 원격에 있던 초기 README.md를 보존하면서 로컬 Spring Boot 초기 프로젝트와 병합했습니다.
- 기본 브랜치를 `main`으로 맞추고 원격 저장소에 push했습니다.

검증 결과:

```text
main 브랜치가 origin/main을 정상 추적합니다.
워크트리는 push 직후 깨끗한 상태였습니다.
```

### 작업 1 - 백엔드 기본 프레임워크 뼈대 구성

완료일: 2026-10-04

완료 내용:

- Spring Boot 버전을 `4.1.1`에서 `3.3.6`으로 낮췄습니다.
- Backend 기본 의존성을 구성했습니다.
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Validation
  - PostgreSQL
  - Flyway
  - QueryDSL
  - Swagger / Springdoc
  - Spring Batch
  - Apache POI
  - JWT
- `application.properties`를 `application.yml`로 변경했습니다.
- 테스트용 `application-test.yml`을 추가했습니다.
- 테스트 환경에서는 H2 DB를 사용하도록 구성했습니다.
- 공통 감사 컬럼용 `BaseEntity`를 추가했습니다.
- 공통 API 응답 구조를 추가했습니다.
  - `ApiResponse`
  - `FieldErrorResponse`
- 공통 예외 처리 구조를 추가했습니다.
  - `BusinessException`
  - `ErrorCode`
  - `GlobalExceptionHandler`
- 설정 클래스를 추가했습니다.
  - `JpaAuditingConfig`
  - `OpenApiConfig`
  - `SecurityConfig`
- 업무 Enum을 추가했습니다.
  - `UserRole`
  - `AttendanceType`
  - `RequestStatus`
  - `ApprovalStatus`
  - `ApprovalActionType`
  - `BatchJobStatus`
- 새로 작성한 코드에 용도 중심의 주석을 추가했습니다.

검증 결과:

```bash
.\gradlew.bat test
```

```text
BUILD SUCCESSFUL
```

### 작업 1-1 - 프로젝트 문서 정리

완료일: 2026-10-05

완료 내용:

- README.md를 프로젝트 소개 문서로 확장했습니다.
- 기술 스택, 업무 흐름, 역할별 기능, 결재 흐름, 상태값, DB 명명 규칙, ERD 초안을 정리했습니다.
- 외부 평가용으로 보일 수 있는 표현을 제거하고 실제 업무 시스템 설명처럼 정리했습니다.
- 작업 기록을 별도로 관리하기 위해 WORK_LOG.md를 추가했습니다.

검증 결과:

```bash
.\gradlew.bat test
```

```text
BUILD SUCCESSFUL
```

## 다음 작업 후보

### 작업 2 - DB Entity, Repository, Flyway DDL 작성

목표:

- README.md에 정리한 ERD 기준으로 JPA Entity와 Repository를 작성합니다.
- Flyway 초기 DDL을 작성해 DB 스키마를 명시적으로 관리합니다.

작업 범위:

- `ORGFLOW001` 사용자 Entity 작성
- `ORGFLOW002` 부서 Entity 작성
- `ORGFLOW003` 근태 신청 Entity 작성
- `ORGFLOW004` 결재선 Entity 작성
- `ORGFLOW005` 결재 이력 Entity 작성
- `ORGFLOW006` 근태 현황 Entity 작성
- `ORGFLOW007` 월별 통계 Entity 작성
- `ORGFLOW008` 배치 실행 결과 Entity 작성
- 각 Entity별 Repository 작성
- Flyway `V1__init_schema.sql` 작성
- PK, FK, Unique Index, 조회용 Index, Check Constraint 설정
- Context Load 테스트 검증

주의 사항:

- 테이블명은 `ORGFLOW001`, `ORGFLOW002`처럼 `ORGFLOW` + 3자리 숫자만 사용합니다.
- 컬럼명은 전부 대문자 스네이크 케이스를 사용합니다.
- 모든 업무 테이블은 `BaseEntity`를 상속해서 감사 컬럼을 포함합니다.
- 감사 컬럼의 `CREATED_ID`, `UPDATED_ID`에는 FK를 걸지 않습니다.
- Entity, 메서드, 주요 필드, Enum 값에는 용도 중심의 주석을 작성합니다.

## 이후 작업 계획

### 작업 3 - 인증/인가

- 로그인 API 작성
- JWT 발급 기능 작성
- JWT 검증 필터 작성
- 사용자 인증 객체 구성
- 역할별 접근 제어 적용

### 작업 4 - USER 신청 기능

- 휴가 신청
- 출장 신청
- 내 신청 목록 조회
- 신청 상세 조회
- 신청 취소
- 결재 진행 상태 조회

### 작업 5 - MANAGER 결재 기능

- 내 결재 대기 목록 조회
- 신청 상세 조회
- 승인
- 반려
- 결재 이력 조회
- 순차 결재 규칙 적용

### 작업 6 - ADMIN 관리 기능

- 사용자 관리
- 부서 관리
- 전체 근태 조회
- 통계 조회
- Excel 다운로드
- Batch 실행 결과 확인

### 작업 7 - Batch, Excel, QueryDSL 고도화

- 월별 통계 집계 Batch
- 배치 실행 결과 저장
- 관리자 동적 검색 QueryDSL 적용
- SXSSF 기반 Excel 다운로드

### 작업 8 - React, TypeScript Frontend

- 로그인 화면
- 일반 직원 신청 화면
- 결재자 결재 화면
- 관리자 관리 화면
- API 연동
- 권한별 메뉴 처리
