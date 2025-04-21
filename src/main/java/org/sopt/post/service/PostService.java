package org.sopt.post.service;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostRes;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.repository.PostRepository;
import org.sopt.common.util.PostWriteLimiter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    // Bean 주입을 위한 생성자
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Post 생성
    public Long createPost(final String title) {

        if (PostWriteLimiter.checkPostTime()){
            validateTitle(title);

            // Service에서만 Post 객체 생성
            Post post = new Post(title);

            Post save = postRepository.save(post);

            PostWriteLimiter.updateLastPostedTime();

            return save.getId();
        } else {
            throw new CustomException(ErrorCode.TITLE_LIMIT);
        }

    }

    // Post 목록
    public List<PostRes> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> new PostRes(post.getId(), post.getTitle()))
                .toList();
    }

    // postId로 Post 불러오기
    public PostRes getPostById(final long id) {
        return postRepository.findById(id)
                .map(post -> new PostRes(post.getId(), post.getTitle()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
    }

    // postId로 Post 삭제하기
    public void deletePostById(final long id) {

        if (postRepository.existsById(id)){
            postRepository.deleteById(id);
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
    }

    // 게시글 제목 수정
    public void updatePostTitle(final UpdatePostReq updatePostReq) {

        // 게시글 제목 중복 방지
        validateTitle(updatePostReq.getTitle());

        // Id기반으로 찾기
        Post post = postRepository.findById(updatePostReq.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 업데이트
        post.updateTitle(updatePostReq.getTitle());

        // 자동으로 되지만, 명시적으로 써놓기
        postRepository.save(post);
    }

    // 키워드 검색 기능
    public List<PostRes> searchPostsByKeyword(final String keyword) {

        // 대, 소문자 구분 X
        return postRepository.findByTitleContaining(keyword)
                .stream()
                .map(post -> new PostRes(post.getId(), post.getTitle()))
                .toList();
    }


    /**
     * 내부 로직
     */
    // 게시글 제목 중복 방지
    private void validateTitle(final String title) {

        if (postRepository.existsByTitle(title)){
            throw new CustomException(ErrorCode.CONFLICT_TITLE);
        }
    }

}
