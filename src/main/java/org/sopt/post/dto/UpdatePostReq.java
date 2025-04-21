package org.sopt.post.dto;

public class UpdatePostReq {

    private long id;
    private String title;

    public UpdatePostReq(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public UpdatePostReq() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
