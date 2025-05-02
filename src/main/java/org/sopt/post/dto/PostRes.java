package org.sopt.post.dto;

import org.sopt.post.domain.Post;

public record PostRes(
        String title,
        String content,
        String tagName,
        String userName
) {

    // Post -> DTO
    public static PostRes fromPost(Post post) {
        return new PostRes(post.getTitle(), post.getContent(), post.getTag().getTagName(), post.getUser().getName());
    }
}
