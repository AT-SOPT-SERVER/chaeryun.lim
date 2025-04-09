package org.sopt.domain;

public class Post {
    private int id;
    private String title;

    public Post(int id, String title) {
        validateTitle(title);
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void updateTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    // rich 도메인 구성을 위한 제목 validate
    private void validateTitle(String title){
        if (title.trim().isEmpty()){
            throw new IllegalArgumentException("제목은 공백일 수 없습니다.");
        } else if (title.length() > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘을 수 없습니다.");
        }
    }
}
