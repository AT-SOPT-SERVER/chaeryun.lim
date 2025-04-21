package org.sopt.common.util;

import com.ibm.icu.text.BreakIterator;
import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TitleValidator {

    public static void validateTitle(String title){

        String trim = title.trim();

        // 모든 문자가 채움 문자거나 공백인 경우 필터링
        if (trim.isEmpty() || trim.chars().allMatch(c -> c == '\u3164' || Character.isWhitespace(c))){
            throw new CustomException(ErrorCode.TITLE_NOT_EMPTY);
        } else if (getEmoji(trim).size() > 30) {
            throw new CustomException(ErrorCode.TITLE_TOO_LONG);
        } else if (trim.contains("  ") || trim.contains(" \u3164") || trim.contains("\u3165 ")) {
            throw new CustomException(ErrorCode.TITLE_CANNOT_CONTAIN_CONSECUTIVE_SPACES);
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
