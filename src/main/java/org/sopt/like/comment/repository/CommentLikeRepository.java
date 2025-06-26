package org.sopt.like.comment.repository;

import org.sopt.like.comment.domain.CommentLike;
import org.sopt.like.comment.dto.CommentLikeCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);

    @Query("""
    SELECT cl.id FROM CommentLike cl
    WHERE cl.user.id = :userId AND cl.comment.id IN :commentIds
""")
    List<Long> findAllByUserIdAndCommentIds(@Param("userId") Long userId, @Param("commentIds") List<Long> commentIds);

    // Comment 갯수들 파악 (CommentId로 단일 처리하면 N+1 문제 발생)
    @Query("SELECT new org.sopt.like.comment.dto.CommentLikeCountDTO(cl.comment.id, COUNT(cl)) FROM CommentLike cl WHERE cl.comment.id IN :commentIds GROUP BY cl.comment.id")
    List<CommentLikeCountDTO> countByCommentIds(@Param("commentIds") List<Long> commentIds);
}
