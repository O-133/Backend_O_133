package com.example.demo.global.exception;
import com.example.demo.global.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 모든 예외를 잡아서 처리
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception e) {
        // 1. 서버 콘솔에 에러 로그 출력 (디버깅용)
        log.error("🚨 서버 에러 발생: {}", e.getMessage(), e);

        // 2. 클라이언트에게 에러 내용 전달
        // 해커톤 편의를 위해 에러 클래스 이름까지 같이 보내줌 (NullPointerException 등)
        String errorMessage = String.format("[%s] %s", e.getClass().getSimpleName(), e.getMessage());

        return ApiResponse.fail(errorMessage);
    }
}