package org.sopt.post.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String imageName;
    private String imageUrl;

    public Post() {}

    public Post(String title) {
        this.title = title;
    }

    public Post(String title, String imageName, String imageUrl) {
        this.title = title;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }


}
