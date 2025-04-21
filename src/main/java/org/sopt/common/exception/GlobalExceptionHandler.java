package org.sopt.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
