package org.sopt.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public record SuccessRes<T>(
        int code,
        String msg,
        T data
) {

    public static <T> SuccessRes<T> created(T data) {
        return new SuccessRes<T>(201, "응답 성공", data);
    }

    public static <T> SuccessRes<T> ok(T data) {
        return new SuccessRes<T>(200, "응답 성공", data);
    }
}
