package org.sopt.post.service;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.util.PostWriteLimiter;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostRes;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;

    // Bean 주입을 위한 생성자
    public PostService(PostRepository postRepository, AwsS3Service awsS3Service) {
        this.postRepository = postRepository;
        this.awsS3Service = awsS3Service;
    }

    // Post 생성
    @Transactional
    public Long createPost(final String title, MultipartFile file) {

        if (PostWriteLimiter.checkPostTime()){
            validateTitle(title);

            if (!file.isEmpty()){
                String fileName = awsS3Service.uploadFile(file);
                String url = awsS3Service.getUrl(fileName);
                Post post = new Post(title, fileName, url);
                Post save = postRepository.save(post);
                PostWriteLimiter.updateLastPostedTime();

                return save.getId();
            } else {
                // Service에서만 Post 객체 생성
                Post post = new Post(title);
                Post save = postRepository.save(post);
                PostWriteLimiter.updateLastPostedTime();

                return save.getId();
            }

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
    @Transactional
    public void deletePostById(final long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        postRepository.delete(post);
        awsS3Service.deleteFile(post.getImageName());
    }

    // 게시글 제목 수정
    @Transactional
    public void updatePostTitle(final UpdatePostReq updatePostReq) {

        // 게시글 제목 중복 방지
        validateTitle(updatePostReq.title());

        // Id기반으로 찾기
        Post post = postRepository.findById(updatePostReq.id())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 업데이트
        post.updateTitle(updatePostReq.title());

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
