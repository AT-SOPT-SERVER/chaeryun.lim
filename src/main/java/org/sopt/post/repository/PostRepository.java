package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Title 중복 확인
    boolean existsByTitle(String title);

    /**
     * 연결된 Entity는 .으로 접근 해야한다, 여기서 _는 .을 의미, Containing은 %LIKE% 검색
     */
    // 사용자 or 제목 통합 검색
    List<Post> findByTitleOrUser_NameContaining(String title, String username);

    // 검색
    List<Post> findByTitleContaining(String title);

    // 사용자 검색
    List<Post> findByUser_NameContaining(String username);

    // 태그 기반 검색
    List<Post> findByTag(Tag tag);

    // 전체 조회 (최신 순)
    List<Post> findAllByOrderByCreatedAtDesc();

    // 게시글 조회 (userId, postId 기반)
    Optional<Post> findByIdAndUserId(Long id, Long userId);
}
