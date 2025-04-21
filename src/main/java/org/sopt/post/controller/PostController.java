package org.sopt.post.controller;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.util.TitleValidator;
import org.sopt.post.dto.PostRequest;
import org.sopt.post.dto.PostRes;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody final PostRequest postRequestDTO){
        // 유효성 검사
        TitleValidator.validateTitle(postRequestDTO.getTitle());

        Long postId = postService.createPost(postRequestDTO.getTitle());

        return ResponseEntity.created(URI.create("/posts/" + postId))
                .body("응답 성공");
    }

    // 모든 게시글 가져오기
    @GetMapping
    public ResponseEntity<List<PostRes>> getPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // postId로 게시글 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<PostRes> getPost(@PathVariable(name = "id") final long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // postId로 게시글 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") final long id){

        postService.deletePostById(id);
        return ResponseEntity.ok("응답 성공");
    }

    // postId로 게시글을 가져와서 수정하기
    @PutMapping
    public ResponseEntity<String> updatePost(@RequestBody final UpdatePostReq updatePostReq){
        // 유효성 검사
        TitleValidator.validateTitle(updatePostReq.getTitle());

        postService.updatePostTitle(updatePostReq);
        return ResponseEntity.ok("응답 성공");
    }

    // 키워드 검색 기능
    @GetMapping("/search")
    public ResponseEntity<List<PostRes>> searchPosts(@RequestParam(name = "keyword") final String keyword){

        if (keyword.isEmpty()){
            throw new CustomException(ErrorCode.CHAERYUN_ERROR);
        }
        return ResponseEntity.ok(postService.searchPostsByKeyword(keyword));
    }
}
