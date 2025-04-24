package org.sopt.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 전용 핸들러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorRes> customExceptionHandler(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorRes.from(errorCode));
    }

    // 해당하는 url이 없는 NoHadlerException 핸들러
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorRes> exceptionHandler(NoHandlerFoundException exception) {
        ErrorCode notSupportedUrl = ErrorCode.NOT_SUPPORTED_URL;

        return ResponseEntity
                .status(notSupportedUrl.getHttpStatus())
                .body(ErrorRes.from(notSupportedUrl));
    }

    // 파라미터 값이 누락된 경우, MissingServletRequestParameterException 핸들러
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorRes> exceptionHandler(MissingServletRequestParameterException exception) {
        ErrorCode emptyParameter = ErrorCode.EMPTY_PARAMETER;

        return ResponseEntity
                .status(emptyParameter.getHttpStatus())
                .body(ErrorRes.from(emptyParameter));
    }

    // 상정하지 못한 Exception 전체 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRes> exceptionHandler(Exception exception) {
        ErrorCode internalServerError = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(internalServerError.getHttpStatus())
                .body(ErrorRes.from(internalServerError));
    }

}
