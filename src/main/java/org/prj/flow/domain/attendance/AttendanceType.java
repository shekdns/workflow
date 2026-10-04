package org.prj.flow.domain.attendance;

/**
 * 근태 신청 및 근태 현황에서 사용하는 근태 유형입니다.
 */
public enum AttendanceType {
  /** 휴가 신청 또는 휴가 현황을 의미합니다. */
  LEAVE,

  /** 출장 신청 또는 출장 현황을 의미합니다. */
  BUSINESS_TRIP
}
