package org.sopt.domain;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

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

        String trim = title.trim();

        // 모든 문자가 채움 문자거나 공백인 경우 필터링
        if (trim.isEmpty() || trim.chars().allMatch(c -> c == '\u3164' || Character.isWhitespace(c))){
            throw new IllegalArgumentException("제목은 공백일 수 없습니다.");
        } else if (getGraphemes(trim).size() > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘을 수 없습니다.");
        } else if (trim.contains("  ") || trim.contains(" \u3164") || trim.contains("\u3165 ")) {
            throw new IllegalArgumentException("연속된 공백은 사용할 수 없습니다.");
        }
    }

    // 이모지 확인 및 분리
    private List<String> getGraphemes(String emoji){
        BreakIterator iterator = BreakIterator.getWordInstance();
        iterator.setText(emoji);

        List<String> graphemes = new ArrayList<>();
        for(int current = iterator.first(), next = iterator.next(); next != BreakIterator.DONE; current = next, next = iterator.next()) {
            graphemes.add(emoji.substring(current, next));
        }

//        System.out.println(graphemes.size());
        return graphemes;
    }
}
