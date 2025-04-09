package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    public void createPost(final Post post) {
        postRepository.save(post);
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

        if (byPostById != null){
            byPostById.updateTitle(newTitle);
            return true;
        } else {
            return false;
        }
    }
}
