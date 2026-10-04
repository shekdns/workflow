package org.prj.flow.domain.approval;

/**
 * 결재자가 실제로 수행한 결재 행위 유형입니다.
 */
public enum ApprovalActionType {
  /** 결재자가 신청을 승인한 경우입니다. */
  APPROVED,

  /** 결재자가 신청을 반려한 경우입니다. */
  REJECTED
}
