package org.sopt.util;

import java.util.concurrent.atomic.AtomicInteger;

public class PostIdGenerator {

    /**
     * === AtomicInteger 사용이유 ===
     * 간단한 Memory 기반 자바 프로젝트로 실제 DB를 사용하지 않아서 AtomicInteger가 적당

     * 1. 내부적으로 CAS 방식을 사용해 동시성 제어 가능
     *   -> 여러 스레드가 동시에 접근하더라도 안전하게 값 갱신 (ex. ID 중복 방지)
     * 2. synchronized보다 성능이 우수함
     *   -> 락을 사용하지 않고도 원자적 연산을 제공하기 때문에 더 가볍고 빠름
     * 3. 다양한 원자 연산 메서드 제공
     *   -> incrementAndGet(), getAndIncrement(), addAndGet() 등 편리한 메서드 제공
     * 4. 스레드 간 가시성을 보장함
     *   -> 변경된 값을 다른 스레드가 즉시 볼 수 있음
     */
    private static final AtomicInteger postId = new AtomicInteger(0);

    // ID 생성 (현재 값 반환 후 증가)
    public static int generatePostId() {

        return postId.getAndIncrement();
    }
}
