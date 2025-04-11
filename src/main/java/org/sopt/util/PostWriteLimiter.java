package org.sopt.util;

import java.time.LocalDateTime;

public class PostWriteLimiter {

    private static LocalDateTime lastPostedTime;
    private static final int timeLimit = 3;

    // 게시글 작성 가능 여부 확인
    public static boolean checkPostTime(){

        if (lastPostedTime == null) {
            return true;
        } else {
            // 3분 뒤 작성 가능
            return LocalDateTime.now().isAfter(lastPostedTime.plusMinutes(timeLimit));
        }
    }

    // 게시글 작성시 lastPostedTime 추가
    public static void updateLastPostedTime() {

        lastPostedTime = LocalDateTime.now();
    }

}
