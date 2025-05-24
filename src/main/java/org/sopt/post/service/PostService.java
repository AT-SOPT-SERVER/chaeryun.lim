package org.sopt.post.service;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.util.PostWriteLimiter;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.Tag;
import org.sopt.post.dto.CreatePostRes;
import org.sopt.post.dto.PostListRes;
import org.sopt.post.dto.PostRequest;
import org.sopt.post.dto.PostRes;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    // Bean 주입을 위한 생성자
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Post 생성
    @Transactional
    public CreatePostRes createPost(final Long userId, final PostRequest postRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        if (PostWriteLimiter.checkPostTime()){
            validateTitle(postRequest.title());

            // Service에서만 Post 객체 생성
            Post save = postRepository.save(Post.from(postRequest, user));

            PostWriteLimiter.updateLastPostedTime();

            return CreatePostRes.fromPost(save);
        } else {
            throw new CustomException(ErrorCode.TITLE_LIMIT);
        }

    }

    // Post 목록 전체 조회 (최신순)
    public PostListRes getAllPosts() {

        List<PostListRes.PostListItem> list = postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostListRes.PostListItem::fromPost)
                .toList();

        return new PostListRes(list);
    }

    // postId로 Post 불러오기 (제목, 내용, 게시글 작성자)
    public PostRes getPostById(final long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        return PostRes.fromPost(post);
    }

    // postId로 Post 삭제하기
    @Transactional
    public void deletePostById(final long id) {

        if (!postRepository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
        postRepository.deleteById(id);
    }

    // 게시글 제목 수정
    @Transactional
    public void updatePostTitle(final Long userId, final UpdatePostReq updatePostReq) {

        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        // 게시글 제목 중복 방지
        validateTitle(updatePostReq.title());

        // Id기반으로 찾기
        Post post = postRepository.findByIdAndUserId(updatePostReq.id(), userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));

        // 업데이트
        post.update(updatePostReq.title(), updatePostReq.content());

        // 자동으로 되지만, 명시적으로 써놓기
        postRepository.save(post);
    }

    // 통합 검색 기능
    public PostListRes searchPostsByKeywordAndUsername(final String keyword, final String username) {

        List<PostListRes.PostListItem> list = postRepository.findByTitleOrUser_NameContaining(keyword, username)
                .stream()
                .map(PostListRes.PostListItem::fromPost)
                .toList();

        return new PostListRes(list);
    }

    // 키워드 검색 기능
    public PostListRes searchPostsByKeyword(final String keyword) {

        // 대, 소문자 구분 X
        List<PostListRes.PostListItem> list = postRepository.findByTitleContaining(keyword)
                .stream()
                .map(PostListRes.PostListItem::fromPost)
                .toList();

        return new PostListRes(list);
    }

    // 작성자로 검색 기능
    public PostListRes searchPostsByUsername(final String username) {

        List<PostListRes.PostListItem> list = postRepository.findByUser_NameContaining(username)
                .stream()
                .map(PostListRes.PostListItem::fromPost)
                .toList();

        return new PostListRes(list);
    }

    // 태그 기반 검색
    public PostListRes searchTags(final String tagName){

        Tag tag = Tag.fromTagName(tagName);

        List<PostListRes.PostListItem> list = postRepository.findByTag(tag)
                .stream()
                .map(PostListRes.PostListItem::fromPost)
                .toList();

        return new PostListRes(list);
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
