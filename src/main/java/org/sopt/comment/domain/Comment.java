package org.sopt.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private Comment(String comment, User user, Post post) {
        this.comment = comment;
        this.user = user;
        this.post = post;
    }

    public static Comment toComment(String comment, User user, Post post) {
        return Comment.builder()
                .comment(comment)
                .user(user)
                .post(post).build();
    }
}
