package org.sopt.post.domain;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;

public enum Tag {
    BACKEND("백엔드"),
    DATABASE("데이터베이스"),
    INFRA("인프라");

    private final String tagName;

    Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    // 넘어온 한글에 해당하는 Tag 찾기 (비효율적인가..?)
    public static Tag fromTagName(String tagName) {
        for (Tag tag : Tag.values()) {
            if (tag.getTagName().equals(tagName)) {
                return tag;
            }
        }

        // 태그 못찾으면
        throw new CustomException(ErrorCode.NOT_FOUND_TAG);
    }
}
