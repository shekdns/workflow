package org.prj.flow.common.response;

/**
 * 요청 DTO 검증 실패 시 필드별 오류 정보를 담는 응답 객체입니다.
 *
 * @param field 검증에 실패한 필드명
 * @param message 검증 실패 사유
 */
public record FieldErrorResponse(
    String field,
    String message
) {
}
