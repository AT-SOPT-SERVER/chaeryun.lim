package org.sopt.user.dto;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;

import java.util.Objects;

public record CreateUserReq(
        String name
) {

    public CreateUserReq {
        // Null 및 공백 체킹
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.USERNAME_NOT_EMPTY);
        }
    }
}
