# WorkFlow

WorkFlow는 기업의 근태 신청과 전자결재 흐름을 관리하는 업무 시스템입니다.

직원이 휴가 또는 출장을 신청하면 결재선이 생성되고, 팀장과 부서장이 순차적으로 승인 또는 반려합니다. 최종 승인된 신청은 근태 현황에 반영되고, 월별 통계와 관리자 조회 기능에서 활용됩니다.

## 기술 스택

| 구분 | 기술 |
| --- | --- |
| Language | Java 17 |
| Backend | Spring Boot 3.x |
| Security | Spring Security, JWT |
| ORM | Spring Data JPA |
| 동적/복잡 조회 | QueryDSL |
| Database | PostgreSQL |
| Frontend | React, TypeScript |
| API Docs | Swagger, Springdoc |
| Batch | Spring Batch |
| Excel | Apache POI SXSSF |
| Test | JUnit5 |
| Infra | Docker |
| CI/CD | GitHub Actions |
| Cloud | AWS EC2, AWS RDS |

## 프로젝트 목적

- 기업 근태 신청 업무를 REST API 기반으로 구현합니다.
- 신청, 결재선, 결재 이력, 근태 반영, 통계 집계의 흐름을 분리해서 설계합니다.
- Spring Security와 JWT를 사용해 역할별 기능 접근을 제어합니다.
- JPA를 기본으로 사용하고, 관리자 검색과 통계처럼 조건이 복잡한 조회는 QueryDSL로 처리합니다.
- Flyway를 사용해 DDL 변경 이력을 관리합니다.
- Docker, GitHub Actions, AWS 배포까지 고려한 구조로 확장합니다.

## 사용자 역할

### USER - 일반 직원

일반 직원은 본인의 근태 신청과 신청 상태 조회를 담당합니다.

- 휴가 신청
- 출장 신청
- 신청 내역 조회
- 신청 취소
- 결재 진행 상태 조회

### MANAGER - 결재자

결재자는 본인에게 올라온 결재 건을 순서에 맞게 처리합니다.

- 결재 대기 목록 조회
- 신청 상세 조회
- 승인
- 반려
- 결재 이력 조회

### ADMIN - 관리자

관리자는 회사 전체 기준 데이터를 관리하고 근태 현황과 통계를 확인합니다.

- 사용자 관리
- 부서 관리
- 전체 근태 조회
- 통계 조회
- Excel 다운로드
- Batch 실행 결과 확인

## 근태 신청 유형

초기 구현 범위에서는 아래 두 가지 신청 유형만 사용합니다.

| 값 | 의미 |
| --- | --- |
| LEAVE | 휴가 |
| BUSINESS_TRIP | 출장 |

초과근무는 초기 범위에서 제외하며, 추후 `OVERTIME` 유형으로 확장할 수 있습니다.

## 업무 흐름

휴가 신청 예시는 다음 순서로 처리됩니다.

```text
사용자 로그인
  ↓
10월 15일 연차 신청
  ↓
ATTENDANCE_REQUEST 생성
  ↓
결재선 생성
  ↓
팀장 결재 대기
  ↓
팀장 승인
  ↓
부서장 결재 대기
  ↓
부서장 승인
  ↓
APPROVED
  ↓
결재 이력 저장
  ↓
근태 현황 반영
  ↓
월별 통계 반영
```

신청이 생성되었다고 바로 근태 현황에 반영하지 않습니다. 최종 승인 상태가 된 신청만 근태 현황에 반영합니다.

## 결재 흐름

결재는 순차 결재 방식입니다.

```text
사원
  ↓
팀장
  ↓
부서장
  ↓
최종 승인
```

예시 결재선은 다음과 같습니다.

```text
1 김팀장 MANAGER
2 박부장 MANAGER
```

핵심 규칙은 아래와 같습니다.

- 1번 결재자가 승인하지 않으면 2번 결재자는 처리할 수 없습니다.
- 현재 차례의 결재선만 `PENDING` 상태가 됩니다.
- 아직 차례가 아닌 결재선은 `WAITING` 상태입니다.
- 중간 결재자가 반려하면 신청 전체가 `REJECTED` 상태가 됩니다.
- 마지막 결재자가 승인하면 신청 전체가 `APPROVED` 상태가 됩니다.

초기 결재선 상태:

```text
신청 상태: PENDING

결재선 1 김팀장: PENDING
결재선 2 박부장: WAITING
```

1차 결재자 승인 후:

```text
신청 상태: PENDING

결재선 1 김팀장: APPROVED
결재선 2 박부장: PENDING
```

최종 결재자 승인 후:

```text
신청 상태: APPROVED

결재선 1 김팀장: APPROVED
결재선 2 박부장: APPROVED
```

## 상태값

### 신청 상태

| 값 | 의미 |
| --- | --- |
| PENDING | 결재 진행 중 |
| APPROVED | 최종 승인 |
| REJECTED | 반려 |
| CANCELED | 신청 취소 |

### 결재선 상태

| 값 | 의미 |
| --- | --- |
| WAITING | 아직 결재 차례가 아님 |
| PENDING | 현재 결재 대기 |
| APPROVED | 해당 결재자 승인 완료 |
| REJECTED | 해당 결재자 반려 완료 |

## DB 명명 규칙

테이블명은 의미 이름을 붙이지 않고 `ORGFLOW` + 3자리 숫자 형식으로 사용합니다.

```text
ORGFLOW001
ORGFLOW002
ORGFLOW003
...
```

컬럼명은 전부 대문자 스네이크 케이스를 사용합니다.

```text
USER_ID
DEPARTMENT_ID
REQUEST_TYPE
REQUEST_STATUS
CREATED_DATE
UPDATED_DATE
```

Enum 값도 전부 대문자로 관리합니다.

```text
USER
MANAGER
ADMIN

LEAVE
BUSINESS_TRIP

PENDING
APPROVED
REJECTED
CANCELED
WAITING
```

## 공통 감사 컬럼

모든 업무 테이블은 아래 공통 컬럼을 포함합니다.

| 컬럼명 | 의미 |
| --- | --- |
| CREATED_ID | 생성자 ID |
| CREATED_DATE | 생성일시 |
| UPDATED_ID | 수정자 ID |
| UPDATED_DATE | 수정일시 |

감사 컬럼의 `CREATED_ID`, `UPDATED_ID`는 단순 기록용 값이며 FK로 강제하지 않습니다.

## ERD 초안

### 테이블 목록

| 테이블 | 의미 |
| --- | --- |
| ORGFLOW001 | 사용자 |
| ORGFLOW002 | 부서 |
| ORGFLOW003 | 근태 신청 |
| ORGFLOW004 | 결재선 |
| ORGFLOW005 | 결재 이력 |
| ORGFLOW006 | 근태 현황 |
| ORGFLOW007 | 월별 통계 |
| ORGFLOW008 | 배치 실행 결과 |

### ORGFLOW001 - 사용자

```text
USER_ID PK
DEPARTMENT_ID FK
LOGIN_ID
PASSWORD
USER_NAME
EMAIL
PHONE
ROLE
POSITION_NAME
ENABLED
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

### ORGFLOW002 - 부서

```text
DEPARTMENT_ID PK
DEPARTMENT_NAME
PARENT_DEPARTMENT_ID FK, nullable
DEPARTMENT_MANAGER_ID FK, nullable
ENABLED
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

`DEPARTMENT_MANAGER_ID`는 부서장을 의미하며 사용자 테이블의 `USER_ID`를 참조합니다.

### ORGFLOW003 - 근태 신청

```text
ATTENDANCE_REQUEST_ID PK
USER_ID FK
REQUEST_TYPE
REQUEST_STATUS
START_DATE
END_DATE
REASON
CANCELED_DATE
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

### ORGFLOW004 - 결재선

```text
APPROVAL_LINE_ID PK
ATTENDANCE_REQUEST_ID FK
APPROVER_ID FK
APPROVAL_ORDER
APPROVAL_STATUS
APPROVED_DATE
REJECTED_DATE
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

### ORGFLOW005 - 결재 이력

```text
APPROVAL_HISTORY_ID PK
ATTENDANCE_REQUEST_ID FK
APPROVAL_LINE_ID FK
APPROVER_ID FK
ACTION_TYPE
COMMENT
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

### ORGFLOW006 - 근태 현황

```text
ATTENDANCE_RECORD_ID PK
ATTENDANCE_REQUEST_ID FK
USER_ID FK
ATTENDANCE_TYPE
START_DATE
END_DATE
REFLECTED_DATE
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

### ORGFLOW007 - 월별 통계

```text
MONTHLY_STATISTICS_ID PK
USER_ID FK
DEPARTMENT_ID FK
STAT_YEAR
STAT_MONTH
LEAVE_COUNT
BUSINESS_TRIP_COUNT
TOTAL_ATTENDANCE_COUNT
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

### ORGFLOW008 - 배치 실행 결과

```text
BATCH_RESULT_ID PK
JOB_NAME
JOB_STATUS
STARTED_DATE
ENDED_DATE
TOTAL_COUNT
SUCCESS_COUNT
FAIL_COUNT
ERROR_MESSAGE
CREATED_ID
CREATED_DATE
UPDATED_ID
UPDATED_DATE
```

## 테이블 관계

```text
ORGFLOW002 1 ─ N ORGFLOW001
부서 1개에는 사용자 여러 명이 속합니다.

ORGFLOW001 1 ─ N ORGFLOW003
사용자 1명은 근태 신청 여러 건을 생성할 수 있습니다.

ORGFLOW003 1 ─ N ORGFLOW004
근태 신청 1건은 여러 결재선을 가집니다.

ORGFLOW001 1 ─ N ORGFLOW004
사용자 1명은 여러 결재선의 결재자가 될 수 있습니다.

ORGFLOW003 1 ─ N ORGFLOW005
근태 신청 1건은 여러 결재 이력을 가질 수 있습니다.

ORGFLOW004 1 ─ 0..1 ORGFLOW005
결재선 1개는 승인 또는 반려 시 결재 이력 1개를 남깁니다.

ORGFLOW003 1 ─ 0..1 ORGFLOW006
근태 신청 1건은 최종 승인 후 근태 현황 1건으로 반영됩니다.

ORGFLOW001 1 ─ N ORGFLOW006
사용자 1명은 여러 근태 현황을 가질 수 있습니다.

ORGFLOW001 1 ─ N ORGFLOW007
사용자 1명은 여러 월별 통계를 가질 수 있습니다.

ORGFLOW002 1 ─ N ORGFLOW007
부서 1개는 여러 월별 통계를 가질 수 있습니다.
```

## 개발 진행 단계

### 작업 1 - 기본 프레임워크 뼈대

완료된 작업입니다.

- Spring Boot 3.x 버전 조정
- 기본 의존성 구성
- 공통 응답 구조 추가
- 공통 예외 처리 구조 추가
- JPA 감사 컬럼 기반 클래스 추가
- Swagger 설정 추가
- Security 기본 설정 추가
- 업무 Enum 추가
- 테스트 프로파일 구성

### 작업 2 - DB Entity, Repository, Flyway DDL

다음 작업 후보입니다.

- 8개 업무 테이블 Entity 생성
- Repository 생성
- 테이블명과 컬럼명을 DB 명명 규칙에 맞춰 매핑
- Flyway `V1__init_schema.sql` 작성
- PK, FK, Unique Index, 조회용 Index, Check Constraint 구성
- Context Load 테스트 검증

### 작업 3 - 인증/인가

- 로그인 API
- JWT 발급
- JWT 검증 필터
- 사용자 인증 객체 구성
- 역할별 접근 제어

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
