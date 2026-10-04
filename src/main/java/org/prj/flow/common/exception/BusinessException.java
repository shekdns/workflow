package org.prj.flow.common.exception;

import lombok.Getter;

/**
 * 업무 규칙 위반이나 처리 불가능한 상태를 표현하는 사용자 정의 예외입니다.
 *
 * <p>서비스 계층에서 ErrorCode와 함께 던지면 GlobalExceptionHandler가
 * API 표준 오류 응답으로 변환합니다.</p>
 */
@Getter
public class BusinessException extends RuntimeException {

  /** HTTP 상태, 오류 코드, 기본 메시지를 담고 있는 오류 유형입니다. */
  private final ErrorCode errorCode;

  /**
   * ErrorCode의 기본 메시지를 사용해서 업무 예외를 생성합니다.
   *
   * @param errorCode 발생한 오류 유형
   */
  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  /**
   * ErrorCode는 유지하되 상황별 상세 메시지를 따로 지정해서 업무 예외를 생성합니다.
   *
   * @param errorCode 발생한 오류 유형
   * @param message 응답에 사용할 상세 오류 메시지
   */
  public BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }
}
