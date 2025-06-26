package org.sopt.like.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.like.post.domain.PostLike;
import org.sopt.like.post.dto.PostLikeCountDTO;
import org.sopt.like.post.repository.PostLikeRepository;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.service.PostService;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeServiceImpl implements PostLikeService {

    private final UserService userService;
    private final PostService postService;
    private final PostLikeRepository postLikeRepository;

    // 좋아요 생성
    @Transactional
    @Override
    public void createPostLike(long userId, long postId) {
        User userById = userService.getUserById(userId);
        Post post = postService.getPost(postId);

        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new CustomException(ErrorCode.CHAERYUN_ERROR);
        }

        PostLike postLike = PostLike.of(userById, post);

        postLikeRepository.save(postLike);
    }

    // 삭제
    @Transactional
    @Override
    public void deletePostLike(long userId, long postId) {
        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresentOrElse(postLikeRepository::delete,
                        () -> {
                    throw new CustomException(ErrorCode.CHAERYUN_ERROR);
                });
    }

    // 좋아요 조회
    @Override
    public List<Long> getPostLikesWithUserId(long userId, List<Long> postIds) {
        return postLikeRepository.findAllByUserIdAndPostIds(userId, postIds);
    }

    // 게시글 좋아요 갯수 조회
    @Override
    public List<PostLikeCountDTO> getPostLikes(List<Long> postIds) {
        return postLikeRepository.countByPostIds(postIds);
    }
}
