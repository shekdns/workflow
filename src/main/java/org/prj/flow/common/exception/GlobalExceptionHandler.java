package org.prj.flow.common.exception;

import java.util.List;
import org.prj.flow.common.response.ApiResponse;
import org.prj.flow.common.response.FieldErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러에서 발생한 예외를 공통 API 응답 형식으로 변환하는 전역 예외 처리기입니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 서비스 계층에서 발생한 업무 예외를 처리합니다.
   *
   * @param exception 업무 예외 객체
   * @return 표준 오류 응답
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ApiResponse.error(errorCode.getCode(), exception.getMessage()));
  }

  /**
   * @Valid 검증 실패 예외를 처리합니다.
   *
   * @param exception 요청 DTO 검증 실패 예외
   * @return 필드별 검증 오류 목록을 포함한 표준 오류 응답
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<List<FieldErrorResponse>>> handleValidationException(
      MethodArgumentNotValidException exception
  ) {
    List<FieldErrorResponse> errors = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(this::toFieldErrorResponse)
        .toList();

    ErrorCode errorCode = ErrorCode.INVALID_INPUT;
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage(), errors));
  }

  /**
   * 별도로 처리하지 않은 예외를 서버 오류 응답으로 변환합니다.
   *
   * @param exception 예상하지 못한 예외
   * @return 서버 오류 표준 응답
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
  }

  /**
   * Spring Validation의 FieldError를 API 응답용 객체로 변환합니다.
   *
   * @param fieldError 검증 실패 필드 정보
   * @return 필드명과 메시지를 담은 응답 객체
   */
  private FieldErrorResponse toFieldErrorResponse(FieldError fieldError) {
    return new FieldErrorResponse(
        fieldError.getField(),
        fieldError.getDefaultMessage()
    );
  }
}
