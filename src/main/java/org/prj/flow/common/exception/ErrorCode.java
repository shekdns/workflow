package org.prj.flow.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 오류 응답에 사용할 공통 오류 코드 목록입니다.
 *
 * <p>각 값은 HTTP 상태 코드, 시스템 오류 코드, 사용자에게 전달할 기본 메시지를 가집니다.</p>
 */
@Getter
public enum ErrorCode {
  /** 요청 파라미터나 본문 값이 유효하지 않은 경우 사용합니다. */
  INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON_001", "요청 값이 올바르지 않습니다."),

  /** 로그인하지 않았거나 인증 토큰이 유효하지 않은 경우 사용합니다. */
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_002", "인증이 필요합니다."),

  /** 인증은 되었지만 해당 기능에 접근할 권한이 없는 경우 사용합니다. */
  FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_003", "접근 권한이 없습니다."),

  /** 조회 대상 데이터가 존재하지 않는 경우 사용합니다. */
  NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_004", "요청한 리소스를 찾을 수 없습니다."),

  /** 현재 업무 상태상 요청을 처리할 수 없는 경우 사용합니다. */
  CONFLICT(HttpStatus.CONFLICT, "COMMON_005", "현재 상태에서 처리할 수 없습니다."),

  /** 예상하지 못한 서버 내부 오류가 발생한 경우 사용합니다. */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_999", "서버 오류가 발생했습니다.");

  /** 클라이언트에 반환할 HTTP 상태 코드입니다. */
  private final HttpStatus status;

  /** 프론트엔드나 로그에서 식별할 수 있는 시스템 오류 코드입니다. */
  private final String code;

  /** 클라이언트에 반환할 기본 오류 메시지입니다. */
  private final String message;

  /**
   * 오류 코드 Enum 값을 생성합니다.
   *
   * @param status HTTP 상태 코드
   * @param code 시스템 오류 코드
   * @param message 기본 오류 메시지
   */
  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
