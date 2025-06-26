package org.sopt.post.dto.response;

import org.sopt.post.dto.PostListItem;

import java.util.List;

public record PostListResponse(
    List<PostItem> postItems
) {
    public record PostItem(
            PostListItem postListItem,
            boolean isLike
    ){
        public PostItem(PostListItem postListItem){
            this(postListItem, false);
        }
    }
}
