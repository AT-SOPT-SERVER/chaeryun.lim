package org.sopt.dto;

public class PostRes {

    private Long id;
    private String title;

    public PostRes() {
    }

    public PostRes(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }
}
