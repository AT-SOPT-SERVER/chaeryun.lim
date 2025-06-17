package org.sopt.comment.dto.response;

import java.util.List;

public record CommentListResponse(
        List<CommentResponse> comments
) {
}
