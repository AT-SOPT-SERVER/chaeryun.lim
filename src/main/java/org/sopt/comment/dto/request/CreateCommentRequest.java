package org.sopt.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotBlank(message = "게시글 ID는 필수입니다.")
        long postId,
        @NotBlank(message = "댓글 내용은 필수입니다.") @Size(min = 1, max = 300, message = "댓글 길이는 최대 300자입니다.")
        String comment
) {

}
