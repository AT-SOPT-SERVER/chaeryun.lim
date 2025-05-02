package org.sopt.user.dto;

import org.sopt.user.domain.User;

public record CreateUserRes(
        Long userId,
        String userName
) {

    // Entity -> DTO 변환
    public static CreateUserRes fromUser(User save) {
        return new CreateUserRes(save.getId(), save.getName());
    }
}
