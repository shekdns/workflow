package org.prj.flow.domain.attendance;

/**
 * 근태 신청 건의 전체 결재 진행 상태입니다.
 */
public enum RequestStatus {
  /** 결재가 진행 중인 상태입니다. */
  PENDING,

  /** 모든 결재자가 승인해서 최종 승인된 상태입니다. */
  APPROVED,

  /** 결재자 중 한 명이 반려해서 최종 반려된 상태입니다. */
  REJECTED,

  /** 신청자가 결재 완료 전에 신청을 취소한 상태입니다. */
  CANCELED
}
