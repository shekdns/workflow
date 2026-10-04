package org.prj.flow.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 모든 REST API에서 공통으로 사용하는 응답 포맷입니다.
 *
 * @param success 요청 성공 여부
 * @param code 응답 코드
 * @param message 응답 메시지
 * @param data 실제 응답 데이터
 * @param <T> 응답 데이터 타입
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
    boolean success,
    String code,
    String message,
    T data
) {

  /**
   * 데이터가 있는 성공 응답을 생성합니다.
   *
   * @param data 응답 본문에 담을 데이터
   * @param <T> 응답 데이터 타입
   * @return 성공 API 응답
   */
  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, "SUCCESS", "요청이 정상적으로 처리되었습니다.", data);
  }

  /**
   * 데이터가 없는 성공 응답을 생성합니다.
   *
   * @return 성공 API 응답
   */
  public static ApiResponse<Void> ok() {
    return new ApiResponse<>(true, "SUCCESS", "요청이 정상적으로 처리되었습니다.", null);
  }

  /**
   * 데이터가 없는 오류 응답을 생성합니다.
   *
   * @param code 오류 코드
   * @param message 오류 메시지
   * @param <T> 응답 데이터 타입
   * @return 오류 API 응답
   */
  public static <T> ApiResponse<T> error(String code, String message) {
    return new ApiResponse<>(false, code, message, null);
  }

  /**
   * 상세 데이터가 있는 오류 응답을 생성합니다.
   *
   * @param code 오류 코드
   * @param message 오류 메시지
   * @param data 오류 상세 데이터
   * @param <T> 응답 데이터 타입
   * @return 오류 API 응답
   */
  public static <T> ApiResponse<T> error(String code, String message, T data) {
    return new ApiResponse<>(false, code, message, data);
  }
}
