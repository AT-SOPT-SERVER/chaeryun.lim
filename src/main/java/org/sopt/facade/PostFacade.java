package org.sopt.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.comment.service.CommentService;
import org.sopt.like.comment.dto.CommentLikeCountDTO;
import org.sopt.like.comment.service.CommentLikeService;
import org.sopt.like.post.service.PostLikeService;
import org.sopt.post.dto.PostListItem;
import org.sopt.post.dto.response.PostDetailResponse;
import org.sopt.post.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostFacade {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final PostService postService;
    private final PostLikeService postLikeService;

    // 게시글 리스트 조회 (로그인 O)
    public Object getPostList(long userId){
        // 게시글 전체 조회
        PostListItem allPosts = postService.getAllPosts();
        List<Long> list = allPosts.stream().map(PostListItem::id).toList();

        // 좋아요 조회
        List<Long> postLikesWithUserId = postLikeService.getPostLikesWithUserId(userId, list);



    }

    // 게시글 리스트 조회 (로그인 X)

    // 게시글 상세 조회 및 댓글 조회 (로그인 O)
    public PostDetailResponse getPostDetailAndCommentsWithLogin(Long userId, long postId) {
        PostDetailResponse postById = postService.getPostByIdWithUserId(userId, postId);
        // 게시글에 있는 댓글목록 불러오기
        List<CommentResponse> comments = commentService.getComments(postId).comments();

        List<CommentResponse> commentResponses = buildCommentResponses(comments, userId);

        // 반환할 commentList 생성
        CommentListResponse commentList = CommentListResponse.builder()
                .comments(commentResponses)
                .build();

        return PostDetailResponse.from(postById.postRes(), commentList);
    }

    // 게시글 상세 조회 (로그인 X)
    public PostDetailResponse getPostDetailAndComments(long postId) {
        PostDetailResponse postById = postService.getPostById(postId);
        // 게시글에 있는 댓글목록 불러오기
        List<CommentResponse> comments = commentService.getComments(postId).comments();

        List<CommentResponse> commentResponses = buildCommentResponses(comments, null); // 비로그인: userId null

        // 반환할 commentList 생성
        CommentListResponse commentList = CommentListResponse.builder()
                .comments(commentResponses)
                .build();

        return PostDetailResponse.from(postById.postRes(), commentList);
    }

    /**
     * 공통 로직
     */
    private List<CommentResponse> buildCommentResponses(List<CommentResponse> comments, Long userId) {
        // 댓글 목록 id값 리스트
        List<Long> commentIdList = comments.stream()
                .map(CommentResponse::commentId)
                .toList();

        // 좋아요 개수 파악
        List<CommentLikeCountDTO> commentLikes = commentLikeService.getCommentLikes(commentIdList);

        // 좋아요 개수 Map 생성
        Map<Long, Long> likeCountMap = commentLikes.stream()
                .collect(Collectors.toMap(CommentLikeCountDTO::commentId, CommentLikeCountDTO::likeCount));

        // 로그인 상태면 좋아요 누른 댓글 ID들 가져오기
        // 비로그인 경우 empty
        List<Long> userLikedCommentIds = (userId != null)
                ? commentLikeService.getCommentLikes(userId, commentIdList)
                : Collections.emptyList();

        // 반환 commentResponse 생성
        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .userId(comment.userId())
                        .username(comment.username())
                        .commentId(comment.commentId())
                        .comment(comment.comment())
                        .isLike(userLikedCommentIds.contains(comment.commentId()))
                        .likeCount(likeCountMap.getOrDefault(comment.commentId(), 0L))
                        .build())
                .toList();
    }
}
