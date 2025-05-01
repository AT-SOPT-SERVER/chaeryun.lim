package org.sopt.post.dto;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.post.domain.Tag;

import java.util.Objects;

public record PostRequest(
        String title,
        String content,
        Tag tag
) {

    public PostRequest {
        // Null 체킹
        Objects.requireNonNull(title);
        Objects.requireNonNull(content);

        if (title.length() > 30){
            throw new CustomException(ErrorCode.TITLE_TOO_LONG);
        }
        if (content.length() > 1000){
            throw new CustomException(ErrorCode.CONTENT_TOO_LONG);
        }
    }

}
