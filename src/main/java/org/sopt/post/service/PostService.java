package org.sopt.post.service;

import org.sopt.post.domain.Post;
import org.sopt.post.dto.CreatePostRes;
import org.sopt.post.dto.PostListItem;
import org.sopt.post.dto.PostRequest;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.dto.response.PostDetailResponse;

import java.util.List;

public interface PostService {

    CreatePostRes createPost(final Long userId, final PostRequest postRequest);

    List<PostListItem> getAllPosts();

    PostDetailResponse getPostByIdWithUserId(final Long userId, final long postId);

    PostDetailResponse getPostById(final long postId);

    Post getPost(final long postId);

    void deletePostById(final long id);

    void updatePostTitle(final Long userId, final UpdatePostReq updatePostReq);

    List<PostListItem> searchPostsByKeywordAndUsername(final String keyword, final String username);

    List<PostListItem> searchPostsByKeyword(final String keyword);

    List<PostListItem> searchPostsByUsername(final String username);

    List<PostListItem> searchTags(final String tagName);


}
