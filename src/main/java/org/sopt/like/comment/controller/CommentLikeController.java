package org.sopt.like.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CreateCommentRequest;
import org.sopt.common.response.SuccessRes;
import org.sopt.like.comment.dto.request.CreateCommentLikeRequest;
import org.sopt.like.comment.service.CommentLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<SuccessRes<Void>> createCommentLike(@RequestHeader(name = "userId") long userId,
                                                           @RequestBody CreateCommentLikeRequest request){
        commentLikeService.createCommentLike(userId, request);

        return ResponseEntity.ok(SuccessRes.ok(null));
    }
}
