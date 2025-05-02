package org.sopt.post.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.sopt.post.dto.PostRequest;
import org.sopt.user.domain.User;

import java.time.LocalDateTime;

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

    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    private Tag tag;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {}

    private Post(String title, String content, Tag tag, User user) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void update(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Tag getTag() {
        return tag;
    }

    // 게시글 생성
    public static Post from(PostRequest postRequest, User user) {
        return new Post(postRequest.title(), postRequest.content(), postRequest.tag(), user);
    }

}
