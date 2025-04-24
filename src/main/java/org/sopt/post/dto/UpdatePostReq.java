package org.sopt.post.dto;

import java.util.Objects;

public record UpdatePostReq(
        long id,
        String title
) {

    public UpdatePostReq {
        Objects.requireNonNull(title);
    }
}
