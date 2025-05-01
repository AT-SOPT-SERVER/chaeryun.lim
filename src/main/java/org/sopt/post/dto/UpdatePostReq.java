package org.sopt.post.dto;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;

public record UpdatePostReq(
        long id,
        String title,
        String content
) {

    public UpdatePostReq {
        if (title == null || title.isBlank()){
            throw new CustomException(ErrorCode.TITLE_NOT_EMPTY);
        }

        if (content == null || content.isBlank()){
            throw new CustomException(ErrorCode.CONTENT_NOT_EMPTY);
        }
    }
}
