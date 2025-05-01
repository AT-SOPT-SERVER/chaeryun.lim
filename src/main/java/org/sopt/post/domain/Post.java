package org.sopt.post.domain;

import jakarta.persistence.*;
import org.sopt.user.domain.User;

@Entity
@Table(name = "posts")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true, nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
