package org.sopt.post.controller;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.response.SuccessRes;
import org.sopt.common.util.TitleValidator;
import org.sopt.post.dto.CreatePostRes;
import org.sopt.post.dto.PostListRes;
import org.sopt.post.dto.PostRequest;
import org.sopt.post.dto.PostRes;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<SuccessRes<CreatePostRes>> createPost(@RequestBody final PostRequest postRequestDTO,
                                                    @RequestHeader(name = "userId") final Long userId){
        // 유효성 검사
        TitleValidator.validateTitle(postRequestDTO.title());

        CreatePostRes createPostRes = postService.createPost(userId, postRequestDTO);

        return ResponseEntity.created(URI.create("/posts/" + createPostRes.postId()))
                .body(SuccessRes.created(createPostRes));
    }

    // 모든 게시글 가져오기 (최신 순으로 조회 / 제목과 게시글 작성자만 출력)
    @GetMapping
    public ResponseEntity<SuccessRes<PostListRes>> getPosts(){
        return ResponseEntity.ok(SuccessRes.ok(postService.getAllPosts()));
    }

    // postId로 게시글 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<SuccessRes<PostRes>> getPost(@PathVariable(name = "id") final long id){
        return ResponseEntity.ok(SuccessRes.ok(postService.getPostById(id)));
    }

    // postId로 게시글 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessRes<Void>> deletePost(@PathVariable(name = "id") final long id){

        postService.deletePostById(id);
        return ResponseEntity.ok(SuccessRes.ok(null));
    }

    // postId로 게시글을 가져와서 수정하기
    @PutMapping("/{id}")
    public ResponseEntity<SuccessRes<Void>> updatePost(@PathVariable final long id,
                                                    @RequestBody final UpdatePostReq updatePostReq,
                                                    @RequestHeader(name = "userId") final Long userId){
        // 유효성 검사
        TitleValidator.validateTitle(updatePostReq.title());

        postService.updatePostTitle(userId, updatePostReq);
        return ResponseEntity.ok(SuccessRes.ok(null));
    }

    // 키워드 검색 기능
    @GetMapping("/search")
    public ResponseEntity<SuccessRes<PostListRes>> searchPosts(@RequestParam(name = "keyword", required = false) final String keyword,
                                                               @RequestParam(name = "username", required = false) final String username){

        if (!StringUtils.hasText(keyword) && !StringUtils.hasText(username)){
            throw new CustomException(ErrorCode.CHAERYUN_ERROR);
        }
        
        // 둘 다 있으면, 통합 검색
        if (StringUtils.hasText(keyword) && StringUtils.hasText(username)){
            return ResponseEntity.ok(SuccessRes.ok(postService.searchPostsByKeywordAndUsername(keyword, username)));
        } else if (StringUtils.hasText(keyword)){   // keyword만 있는 경우
            return ResponseEntity.ok(SuccessRes.ok(postService.searchPostsByKeyword(keyword)));
        } else {    // username의 경우
            return ResponseEntity.ok(SuccessRes.ok(postService.searchPostsByUsername(username)));
        }
    }

    // 태그 기반 검색
    @GetMapping("/search/tags")
    public ResponseEntity<SuccessRes<PostListRes>> searchTags(@RequestParam(name = "tag") final String tagName){
        if (!StringUtils.hasText(tagName)){
            throw new CustomException(ErrorCode.CHAERYUN_ERROR);
        }

        return ResponseEntity.ok(SuccessRes.ok(postService.searchTags(tagName)));
    }
}
