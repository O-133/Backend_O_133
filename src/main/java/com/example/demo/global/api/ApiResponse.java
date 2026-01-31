package com.example.demo.global.api;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // 성공 시 (데이터 있음)
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "요청 성공", data);
    }

    // 성공 시 (데이터 없음 - 예: 삭제 성공)
    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // 실패 시 (에러 메시지 전송) 
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}