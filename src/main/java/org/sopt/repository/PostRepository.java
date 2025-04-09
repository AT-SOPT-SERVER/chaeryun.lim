package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    List<Post> postList = new ArrayList<>();

    public void save(final Post post){
        postList.add(post);
    }

    public List<Post> findAll(){
        return postList;
    }

    public Post findByPostById(final int id){

        for(Post post : postList){

            if (post.getId() == id){
                return post;
            }
        }

        return null;
    }

    public boolean deleteById(final int id){
        for(Post post : postList){
            if (post.getId() == id){
                return postList.remove(post);
            }
        }

        return false;
    }
}
