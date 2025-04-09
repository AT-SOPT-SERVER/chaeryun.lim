package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private PostService postService = new PostService();
    private int postId;


    public void createPost(String title){
        Post post = new Post(postId++, title);

        postService.createPost(post);
    }

    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    public Post getPostById(int id){
        Post postById = postService.getPostById(id);
        if (postById == null){
            System.err.println("Post not found");
        }
        return postById;
    }

    public boolean deletePostById(int id){

        return postService.deletePostById(id);
    }

    // 게시글 수정
    public boolean updatePostTitle(int id, String newTitle){

        return postService.updatePostTitle(id, newTitle);
    }
}
