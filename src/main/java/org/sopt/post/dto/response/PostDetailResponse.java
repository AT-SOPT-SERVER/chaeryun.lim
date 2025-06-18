package org.sopt.post.dto.response;

import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.post.dto.PostRes;

public record PostDetailResponse(
        PostRes postRes,
        CommentListResponse commentListResponse
) {
    public static PostDetailResponse from(PostRes postRes, CommentListResponse commentListResponse) {
        return new PostDetailResponse(postRes, commentListResponse);
    }
}
