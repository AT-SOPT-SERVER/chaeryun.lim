package org.sopt.comment.dto.response;

import lombok.Builder;
import org.sopt.comment.domain.Comment;

@Builder
public record CommentResponse(
        long userId,
    String username,
    long commentId,
    String comment,
        boolean isLike,
        long likeCount
) {
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .userId(comment.getUser().getId())
                .username(comment.getUser().getName())
                .commentId(comment.getId())
                .comment(comment.getComment())
                .build();
    }

}
