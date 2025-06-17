package org.sopt.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CreateCommentRequest;
import org.sopt.comment.dto.request.UpdateCommentRequest;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.service.CommentService;
import org.sopt.common.response.SuccessRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/posts")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<SuccessRes<Void>> createComment(@RequestHeader("userId") long userId,
                                                       @PathVariable("postId") long postId,
                                                       @RequestBody CreateCommentRequest request) {

        commentService.createComment(userId, postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessRes.created());
    }

    // 댓글들 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<SuccessRes<CommentListResponse>> getComments(@RequestHeader Long userId,
                                                                       @PathVariable long postId){

        if (userId == null){
            return ResponseEntity.ok(SuccessRes.ok(commentService.getComments(postId)));
        } else {
            return ResponseEntity.ok(SuccessRes.ok(commentService.getCommentsWithUserId(userId, postId)));
        }
    }

    // 댓글 수정
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<SuccessRes<Void>> updateComment(@RequestHeader(name = "userId") long userId,
                                                          @PathVariable(name = "postId") long postId,
                                                          @PathVariable(name = "commentId") long commentId,
                                                          @RequestBody UpdateCommentRequest request){

        commentService.updateComment(userId, commentId, request);

        return ResponseEntity.ok(SuccessRes.ok(null));
    }

    // 댓글 삭제
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<SuccessRes<Void>> deleteComment(@RequestHeader(name = "userId") long userId,
                                                          @PathVariable(name = "postId") long postId,
                                                          @PathVariable(name = "commentId") long commentId){

        commentService.deleteComment(userId, commentId);

        return ResponseEntity.ok(SuccessRes.ok(null));
    }
}
