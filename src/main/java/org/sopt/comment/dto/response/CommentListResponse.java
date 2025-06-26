package org.sopt.comment.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentListResponse(
        List<CommentResponse> comments
) {
}
