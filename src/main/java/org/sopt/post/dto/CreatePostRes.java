package org.sopt.post.dto;

import org.sopt.post.domain.Post;

public record CreatePostRes(
        Long postId
) {
    public static CreatePostRes fromPost(Post post) {
        return new CreatePostRes(
                post.getId()
        );
    }
}
