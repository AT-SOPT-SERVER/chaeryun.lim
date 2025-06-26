package org.sopt.post.service;

import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.service.CommentService;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.common.util.PostWriteLimiter;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.Tag;
import org.sopt.post.dto.CreatePostRes;
import org.sopt.post.dto.PostListItem;
import org.sopt.post.dto.PostRequest;
import org.sopt.post.dto.PostRes;
import org.sopt.post.dto.UpdatePostReq;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    // Bean 주입을 위한 생성자
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
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
    public PostListItem getAllPosts() {

        List<PostListItem.PostListItem> list = postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostListItem.PostListItem::fromPost)
                .toList();

        return new PostListItem(list);
    }

    // postId로 Post 불러오기 (제목, 내용, 게시글 작성자)
    public PostDetailResponse getPostByIdWithUserId(final Long userId, final long postId) {
        Post post = getPost(postId);

        CommentListResponse commentsWithUserId = commentService.getCommentsWithUserId(userId, postId);
        return PostDetailResponse.from(PostRes.fromPost(post), commentsWithUserId);
    }

    // postId로 Post 불러오기 (로그인 X)
    public PostDetailResponse getPostById(final long postId){
        Post post = getPost(postId);

        CommentListResponse comments = commentService.getComments(postId);
        return PostDetailResponse.from(PostRes.fromPost(post), comments);
    }

    /**
     * Post 엔티티 반환
     */
    public Post getPost(final long id){
        return postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POST));
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
    public PostListItem searchPostsByKeywordAndUsername(final String keyword, final String username) {

        List<PostListItem.PostListItem> list = postRepository.findByTitleOrUser_NameContaining(keyword, username)
                .stream()
                .map(PostListItem.PostListItem::fromPost)
                .toList();

        return new PostListItem(list);
    }

    // 키워드 검색 기능
    public PostListItem searchPostsByKeyword(final String keyword) {

        // 대, 소문자 구분 X
        List<PostListItem.PostListItem> list = postRepository.findByTitleContaining(keyword)
                .stream()
                .map(PostListItem.PostListItem::fromPost)
                .toList();

        return new PostListItem(list);
    }

    // 작성자로 검색 기능
    public PostListItem searchPostsByUsername(final String username) {

        List<PostListItem.PostListItem> list = postRepository.findByUser_NameContaining(username)
                .stream()
                .map(PostListItem.PostListItem::fromPost)
                .toList();

        return new PostListItem(list);
    }

    // 태그 기반 검색
    public PostListItem searchTags(final String tagName){

        Tag tag = Tag.fromTagName(tagName);

        List<PostListItem.PostListItem> list = postRepository.findByTag(tag)
                .stream()
                .map(PostListItem.PostListItem::fromPost)
                .toList();

        return new PostListItem(list);
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
