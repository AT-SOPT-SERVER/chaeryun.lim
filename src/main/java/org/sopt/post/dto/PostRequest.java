package org.sopt.post.dto;

import java.util.Objects;

public record PostRequest(
        String title
) {

    public PostRequest {
        // Null 체킹
        Objects.requireNonNull(title);
    }

}
