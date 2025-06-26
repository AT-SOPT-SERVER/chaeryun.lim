package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.request.CreateCommentRequest;
import org.sopt.comment.dto.request.UpdateCommentRequest;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.like.comment.service.CommentLikeService;
import org.sopt.post.domain.Post;
import org.sopt.post.service.PostServiceImpl;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostServiceImpl postService;
    private final CommentLikeService commentLikeService;

    // 댓글 작성
    @Transactional
    public void createComment(long userId, long postId, CreateCommentRequest request) {
        User user = userService.getUserById(userId);
        Post post = postService.getPost(postId);

        Comment comment = Comment.toComment(request.comment(), user, post);

        commentRepository.save(comment);
    }

    // 댓글 조회 (로그인 O)
    public CommentListResponse getCommentsWithUserId(long userId, long postId){
        List<Comment> allByPostId = commentRepository.findAllByPostId(postId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : allByPostId) {
            commentResponses.add(CommentResponse.from(comment));
        }

        return new CommentListResponse(commentResponses);
    }

    // 댓글 조회 (로그인 X)
    public CommentListResponse getComments(long postId){
        List<Comment> allByPostId = commentRepository.findAllByPostId(postId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : allByPostId) {
            commentResponses.add(CommentResponse.from(comment));
        }

        return new CommentListResponse(commentResponses);
    }

    // 댓글 수정
    @Transactional
    public void updateComment(long userId, long commentId, UpdateCommentRequest request){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        User userById = userService.getUserById(userId);

        if (comment.getUser().equals(userById)) {
            throw new CustomException(ErrorCode.FORBIDDEN_COMMENT);
        }

        comment.updateComment(request.updateComment());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(long userId, long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        User userById = userService.getUserById(userId);

        if (comment.getUser().equals(userById)) {
            throw new CustomException(ErrorCode.FORBIDDEN_COMMENT);
        }

        commentRepository.delete(comment);
    }
    /**
     * 내부 로직
     */
    // 단일 댓글 조회
    public Comment getComment(long commentId){
        return commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
    }
}
