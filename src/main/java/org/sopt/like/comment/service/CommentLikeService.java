package org.sopt.like.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.service.CommentService;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.like.comment.domain.CommentLike;
import org.sopt.like.comment.dto.CommentLikeCountDTO;
import org.sopt.like.comment.dto.request.CreateCommentLikeRequest;
import org.sopt.like.comment.repository.CommentLikeRepository;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;
    private final UserService userService;

    // 좋아요 조회 (로그인 O)
    public List<Long> getCommentLikes(long userId, List<Long> commentIds) {

        return commentLikeRepository.findAllByUserIdAndCommentIds(userId, commentIds);
    }

    // 좋아요 조회 (로그인 X)
    public List<CommentLikeCountDTO> getCommentLikes(List<Long> commentIds) {

        return commentLikeRepository.countByCommentIds(commentIds);
    }

    // 좋아요 (추가 or 삭제)
    @Transactional
    public void createCommentLike(long userId, CreateCommentLikeRequest request) {
        long commentId = request.commentId();

        commentLikeRepository.findByCommentIdAndUserId(commentId, userId).ifPresentOrElse(
                commentLikeRepository::delete,
                () -> {
                    User user = userService.getUserById(userId);
                    Comment comment = commentService.getComment(commentId);

                    CommentLike commentLike = CommentLike.of(user, comment);

                    commentLikeRepository.save(commentLike);
                }
        );

    }

}
