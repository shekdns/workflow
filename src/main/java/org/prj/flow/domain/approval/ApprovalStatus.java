package org.prj.flow.domain.approval;

/**
 * 신청 건에 연결된 개별 결재선의 진행 상태입니다.
 */
public enum ApprovalStatus {
  /** 아직 앞 순번 결재가 끝나지 않아 처리할 수 없는 상태입니다. */
  WAITING,

  /** 현재 결재자가 승인 또는 반려를 처리해야 하는 상태입니다. */
  PENDING,

  /** 해당 결재자가 승인 처리를 완료한 상태입니다. */
  APPROVED,

  /** 해당 결재자가 반려 처리를 완료한 상태입니다. */
  REJECTED
}
