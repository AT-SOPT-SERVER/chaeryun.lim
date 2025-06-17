package org.sopt.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CreateCommentRequest;
import org.sopt.comment.service.CommentService;
import org.sopt.common.response.SuccessRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<SuccessRes<?>> createComment(@RequestHeader("userId") long userId,
                                                       @RequestBody CreateCommentRequest request) {

        commentService.createComment(userId, request);
    }
}
