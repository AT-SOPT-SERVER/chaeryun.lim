package org.sopt.common.exception;

// 에러 응답 DTO
public record ErrorRes(
        int code,
        String msg
) {
    public static ErrorRes from(ErrorCode errorCode){
        return new ErrorRes(errorCode.getCode(), errorCode.getMessage());
    }
}
