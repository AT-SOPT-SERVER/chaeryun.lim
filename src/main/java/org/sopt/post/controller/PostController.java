package org.sopt.post.controller;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.response.SuccessRes;
import org.sopt.common.util.TitleValidator;
import org.sopt.post.dto.PostRequest;
import org.sopt.post.dto.PostRes;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessRes<?>> createPost(@RequestPart("title") final PostRequest title,
                                                    @RequestPart(value = "file", required = false) final MultipartFile file){

        // 유효성 검사
        TitleValidator.validateTitle(title.title());

        Long postId = postService.createPost(title.title(), file);

        return ResponseEntity.created(URI.create("/posts/" + postId))
                .body(SuccessRes.created(null));
    }

    // 모든 게시글 가져오기
    @GetMapping
    public ResponseEntity<SuccessRes<List<PostRes>>> getPosts(){
        return ResponseEntity.ok(SuccessRes.ok(postService.getAllPosts()));
    }

    // postId로 게시글 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<SuccessRes<PostRes>> getPost(@PathVariable(name = "id") final long id){
        return ResponseEntity.ok(SuccessRes.ok(postService.getPostById(id)));
    }

    // postId로 게시글 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessRes<?>> deletePost(@PathVariable(name = "id") final long id){

        postService.deletePostById(id);
        return ResponseEntity.ok(SuccessRes.ok(null));
    }

    // postId로 게시글을 가져와서 수정하기
    @PutMapping("/{id}")
    public ResponseEntity<SuccessRes<?>> updatePost(@PathVariable final long id, @RequestBody final UpdatePostReq updatePostReq){
        // 유효성 검사
        TitleValidator.validateTitle(updatePostReq.title());

        postService.updatePostTitle(updatePostReq);
        return ResponseEntity.ok(SuccessRes.ok(null));
    }

    // 키워드 검색 기능
    @GetMapping("/search")
    public ResponseEntity<SuccessRes<List<PostRes>>> searchPosts(@RequestParam(name = "keyword") final String keyword){

        if (!StringUtils.hasText(keyword)){
            throw new CustomException(ErrorCode.CHAERYUN_ERROR);
        }
        return ResponseEntity.ok(SuccessRes.ok(postService.searchPostsByKeyword(keyword)));
    }
}
