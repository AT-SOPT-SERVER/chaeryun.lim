# Build Stage
FROM amazoncorretto:17-alpine-jdk as builder

WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# Run Stage
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

ARG PROFILES
ARG ENV

# JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=$PROFILES -Dserver.env=$ENV -jar app.jar"]
