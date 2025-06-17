package org.sopt.like.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentLikeRequest(
        @NotBlank(message = "댓글 ID는 필수입니다.") long commentId
) {
}
