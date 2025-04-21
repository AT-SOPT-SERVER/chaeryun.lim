package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.util.PostIdGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Title 중복 확인
    boolean existsByTitle(String title);

    // 검색
    List<Post> findByTitleContaining(String title);
}
