package org.sopt.like.post.service;

import org.sopt.like.post.dto.PostLikeCountDTO;

import java.util.List;

public interface PostLikeService {

    // 게시글 좋아요 (생성)
    void createPostLike(long userId, long postId);

    // 게시글 삭제
    void deletePostLike(long userId, long postId);

    // 좋아요 조회 (로그인 O)
    List<Long> getPostLikesWithUserId(long userId, List<Long> postIds);

    // 좋아요 조회 (로그인 X)
    List<PostLikeCountDTO> getPostLikes(List<Long> postIds);


}
