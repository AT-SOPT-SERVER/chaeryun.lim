package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Title 중복 확인
    boolean existsByTitle(String title);

    // 검색
    List<Post> findByTitleContaining(String title);
}
