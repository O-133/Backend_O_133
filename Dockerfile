# 1단계: 빌드 스테이지
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Gradle 래퍼와 설정 파일들을 먼저 복사 (캐싱 활용)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .


# 소스 코드 복사 및 빌드
COPY src src
RUN ./gradlew clean bootJar --no-daemon

# 2단계: 실행 스테이지
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일만 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 로그 디렉토리 생성
RUN mkdir ./logs

# 실행 권한 및 포트 설정
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]