package org.sopt.like.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.service.CommentService;
import org.sopt.like.comment.dto.request.CreateCommentLikeRequest;
import org.sopt.like.comment.repository.CommentLikeRepository;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;
    private final UserService userService;

    // 좋아요 조회 (로그인 O)
    public void getCommentLikes(long userId, long commentId) {
//        commentLikeRepository
    }

    // 좋아요 조회 (로그인 X)
    public void getCommentLikes(long commentId) {

    }

    // 좋아요
    @Transactional
    public void createCommentLike(long userId, CreateCommentLikeRequest request) {
        User user = userService.getUserById(userId);
//        commentService.
    }

    // 좋아요 삭제
    @Transactional
    public void deleteCommentLike(){

    }
}
