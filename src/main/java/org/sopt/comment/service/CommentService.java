package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.request.CreateCommentRequest;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.post.domain.Post;
import org.sopt.post.service.PostService;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    // 댓글 작성
    @Transactional
    public void createComment(long userId, CreateCommentRequest request) {
        User user = userService.getUserById(userId);
        Post post = postService.getPost(request.postId());

        Comment comment = Comment.toComment(request.comment(), user, post);

        commentRepository.save(comment);
    }

    // 댓글 조회
    public void getComments(){

    }

    // 댓글 수정
    public void updateComment(){

    }

    // 댓글 삭제
    public void deleteComment(){

    }
}
