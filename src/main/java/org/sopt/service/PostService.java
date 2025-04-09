package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService {
    private PostRepository postRepository = new PostRepository();

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(int id) {
        return postRepository.findByPostById(id);
    }

    public boolean deletePostById(int id) {
        return postRepository.deleteById(id);
    }

    // 게시글 제목 수정
    public boolean updatePostTitle(int id, String newTitle) {

        Post byPostById = postRepository.findByPostById(id);

        if (byPostById != null){
            byPostById.updateTitle(newTitle);
            return true;
        } else {
            return false;
        }
    }
}
