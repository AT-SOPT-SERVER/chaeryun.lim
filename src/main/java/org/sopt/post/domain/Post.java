package org.sopt.post.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Post(String title) {
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public Post() {}

    public Post(Long id, String title) {
        this.id = id;
        this.title = title;
    }

}
