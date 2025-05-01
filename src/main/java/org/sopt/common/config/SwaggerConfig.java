package org.sopt.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "AWS-S3-TEST",
                description = "AWS-S3 Post 테스트",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi SampleOpenApi() {
        String[] paths = {"/**"};       // path별 그룹 짓기

        return GroupedOpenApi.builder()
                .group("api-v1")        // path 기능 명 v1-result
                .pathsToMatch(paths)
                .build();
    }
}
