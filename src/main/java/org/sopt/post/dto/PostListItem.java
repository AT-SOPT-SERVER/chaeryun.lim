package org.sopt.post.dto;

import org.sopt.post.domain.Post;

import java.util.List;

public record PostListItem(
        List<PostListItem> postList
) {
    
    public record PostListItem(
            long id,
            String userName,
            String title,
            String tag
    ) {
        public static PostListItem fromPost(Post post) {
            return new PostListItem(post.getId(), post.getUser().getName(), post.getTitle(), post.getTag().getTagName());
        }
    }
}
