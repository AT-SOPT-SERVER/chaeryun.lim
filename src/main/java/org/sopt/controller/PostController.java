package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // 게시글 생성
    public void createPost(final String title){

        postService.createPost(title);
    }

    // 모든 게시글 가져오기
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    // postId로 게시글 가져오기
    public Post getPostById(final int id){
        Post postById = postService.getPostById(id);
        if (postById == null){
            System.err.println("Post not found");
        }
        return postById;
    }

    // postId로 게시글 삭제하기
    public boolean deletePostById(final int id){

        return postService.deletePostById(id);
    }

    // postId로 게시글을 가져와서 수정하기
    public boolean updatePostTitle(final int id,final String newTitle){

        return postService.updatePostTitle(id, newTitle);
    }
}
