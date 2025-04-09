package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.PostIdGenerator;
import org.sopt.util.PostWriteLimiter;

import java.util.List;
import java.util.stream.Collectors;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    public void createPost(final String title) {

        if (PostWriteLimiter.checkPostTime()){
            validateTitle(title);

            // Service에서만 Post 객체 생성
            Post post = new Post(PostIdGenerator.generatePostId(), title);

            postRepository.save(post);

            PostWriteLimiter.updateLastPostedTime();
        } else {

            throw new IllegalArgumentException("마지막 게시글 작성 시간 3분 후에 작성이 가능합니다.");
        }

    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(final int id) {
        return postRepository.findByPostById(id);
    }

    public boolean deletePostById(final int id) {
        return postRepository.deleteById(id);
    }

    // 게시글 제목 수정
    public boolean updatePostTitle(final int id, final String newTitle) {

        Post byPostById = postRepository.findByPostById(id);

        // 게시글 제목 중복 방지
        validateTitle(newTitle);

        if (byPostById != null){
            byPostById.updateTitle(newTitle);
            return true;
        } else {
            return false;
        }
    }

    // 키워드 검색 기능
    public List<Post> searchPostsByKeyword(final String keyword) {

        // 대, 소문자 구분 X
        return postRepository.findAll().stream()
                .filter(post -> post.getTitle().toLowerCase().contains(keyword))
                .toList();

//        // 대, 소문자 구분 O
//        return postRepository.findAll().stream()
//                .filter(post -> post.getTitle().contains(keyword))
//                .toList();

//        // 양쪽 공백 제거 및 대, 소문자 구분 O
//        return postRepository.findAll().stream()
//                .filter(post -> post.getTitle().trim().toLowerCase().contains(keyword))
//                .toList();
    }


    /**
     * 내부 로직
     */

    // 게시글 제목 중복 방지
    private void validateTitle(final String title) {

        // anyMatch: 하나라도 일치시 true 반환
        boolean check = postRepository.findAll().stream()
                            .anyMatch(post -> post.getTitle().equals(title));

        if (check){
            throw new IllegalArgumentException("제목은 중복일 수 없습니다.");
        }
    }

}
