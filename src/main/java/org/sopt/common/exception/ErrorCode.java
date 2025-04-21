package org.sopt.common.exception;

import org.springframework.http.HttpStatus;

// 사용자 지정 예외 코드
public enum ErrorCode {

    /**
     * 400xx BAD_REQUEST
     */
    TITLE_NOT_EMPTY(40001, HttpStatus.BAD_REQUEST, "제목은 비어있을 수 없습니다."),
    TITLE_TOO_LONG(40002, HttpStatus.BAD_REQUEST,"제목의 길이는 30자를 넘을 수 없습니다."),
    TITLE_LIMIT(40003, HttpStatus.BAD_REQUEST, "마지막 게시글 작성 시간 3분 후에 작성이 가능합니다."),
    TITLE_CANNOT_CONTAIN_CONSECUTIVE_SPACES(40004, HttpStatus.BAD_REQUEST, "제목에 연속된 공백은 포함될 수 없습니다."),

    /**
     * 404xx NOT_FOUND
     */
    NOT_SUPPORTED_URL(40401, HttpStatus.NOT_FOUND, "지원하지 않는 URL입니다."),
    NOT_FOUND_POST(40402, HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),

    /**
     * 405xx METHOD_NOT_ALLOWED
     */
    INVALID_HTTP_METHOD(40501, HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),

    /**
     * 409xx CONFLICT
     */
    CONFLICT_TITLE(40901, HttpStatus.CONFLICT, "중복된 게시글 제목입니다."),

    /**
     * 500xx INTERNAL_SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),

    /**
     * 900 CHAERYUN_ERROR
     */
    CHAERYUN_ERROR(900, HttpStatus.BAD_REQUEST, "으이구 인간아 ᕙ( ︡과젝’︡益’︠)ง 으이구 " +
            "인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠" +
            ")ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠" +
            ")ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง 으이구 인간아 ᕙ" +
            "( ︡’︡益’︠)ง 으이구 인간아 ᕙ( ︡’︡益’︠)ง");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
