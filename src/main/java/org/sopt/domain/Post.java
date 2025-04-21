package org.sopt.domain;

import com.ibm.icu.text.BreakIterator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Post(String title) {
        validateTitle(title);
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void updateTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    public Post() {}

    public Post(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * 내부 로직
     */
    // rich 도메인 구성을 위한 제목 validate
    private void validateTitle(String title){

        String trim = title.trim();

        // 모든 문자가 채움 문자거나 공백인 경우 필터링
        if (trim.isEmpty() || trim.chars().allMatch(c -> c == '\u3164' || Character.isWhitespace(c))){
            throw new IllegalArgumentException("제목은 공백일 수 없습니다.");
        } else if (getEmoji(trim).size() > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘을 수 없습니다.");
        } else if (trim.contains("  ") || trim.contains(" \u3164") || trim.contains("\u3165 ")) {
            throw new IllegalArgumentException("연속된 공백은 사용할 수 없습니다.");
        }
    }

    // 이모지 확인 및 갯수 확인
    private static List<String> getEmoji(String input) {
        BreakIterator iterator = BreakIterator.getCharacterInstance(Locale.ROOT);
        iterator.setText(input);

        List<String> graphemes = new ArrayList<>();
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            graphemes.add(input.substring(start, end));
        }

        return graphemes;
    }
}
