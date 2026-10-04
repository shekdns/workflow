package org.prj.flow.domain.batch;

/**
 * Spring Batch 작업 실행 결과 상태입니다.
 */
public enum BatchJobStatus {
  /** 배치 작업이 시작된 상태입니다. */
  STARTED,

  /** 배치 작업이 정상 완료된 상태입니다. */
  COMPLETED,

  /** 배치 작업이 실패한 상태입니다. */
  FAILED
}
