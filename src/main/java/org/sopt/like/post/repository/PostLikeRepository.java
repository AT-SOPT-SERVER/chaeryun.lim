package org.sopt.like.post.repository;

import org.sopt.like.comment.dto.CommentLikeCountDTO;
import org.sopt.like.post.domain.PostLike;
import org.sopt.like.post.dto.PostLikeCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);


    // postId에 해당하는 post들을 좋아요 눌렀는지
    @Query("""
    SELECT pl.id FROM PostLike pl
    WHERE pl.user.id = :userId AND pl.post.id IN :postIds
""")
    List<Long> findAllByUserIdAndPostIds(@Param("userId") Long userId, @Param("postIds") List<Long> postIds);

    // Post 좋아요 갯수들 파악 (PostId로 단일 처리하면 N+1 문제 발생)
    @Query("SELECT new org.sopt.like.post.dto.PostLikeCountDTO(pl.post.id, COUNT(pl)) FROM PostLike pl WHERE pl.post.id IN :postIds GROUP BY pl.post.id")
    List<PostLikeCountDTO> countByPostIds(@Param("postIds") List<Long> postIds);
}
