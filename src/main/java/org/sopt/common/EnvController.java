package org.sopt.common;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EnvController {

    @Value("${server.env}")
    private String serverEnv;

    // green인지 blue인지 확인
    @Operation(summary = "배포 버전 확인", description = "배포 버전 확인 (블루, 그린)")
    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return Map.of("env", serverEnv);
    }
}
