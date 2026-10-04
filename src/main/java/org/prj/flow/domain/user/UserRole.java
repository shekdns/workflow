package org.prj.flow.domain.user;

/**
 * 시스템 사용자의 권한 역할입니다.
 */
public enum UserRole {
  /** 일반 직원 권한입니다. 근태 신청과 본인 신청 조회를 담당합니다. */
  USER,

  /** 결재자 권한입니다. 본인에게 올라온 결재를 승인 또는 반려합니다. */
  MANAGER,

  /** 관리자 권한입니다. 사용자, 부서, 전체 근태, 통계, 배치 결과를 관리합니다. */
  ADMIN
}
