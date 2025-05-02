package org.sopt.post.dto;

import org.sopt.post.domain.Post;

import java.util.List;

public record PostListRes(
        List<PostListItem> postList
) {

    public record PostListItem(
            String userName,
            String title,
            String tag
    ) {
        public static PostListItem fromPost(Post post) {
            return new PostListItem(post.getUser().getName(), post.getTitle(), post.getTag().getTagName());
        }
    }
}
