package org.sopt.user.domain;

import jakarta.persistence.*;
import org.sopt.post.domain.Post;
import org.sopt.user.dto.CreateUserReq;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    // 기본 생성자
    protected User(){}

    // 이름으로 생성
    private User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    // CreateUserReq -> Entity 변환
    public static User from(CreateUserReq userReq){
        return new User(userReq.name());
    }
}
