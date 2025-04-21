package org.sopt.post.dto;

public class PostRequest {
    private String title;

    public PostRequest(String title) {
        this.title = title;
    }

    public PostRequest() {
    }

    public String getTitle() {
        return title;
    }
}
