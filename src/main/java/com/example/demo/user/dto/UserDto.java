package com.example.demo.user.dto;

import com.example.demo.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
public class UserDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRequest {
        private String nickname;
        private String gender;
        private int age;
        private double height;
        private double weight;
        private List<String> diseaseNames;
        private List<String> medications;
        private boolean isCooksAtHome;
    }

    @Getter
    @Builder
    public static class Response {
        private Integer id;
        private String nickname;
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateRequest {
        private int age;
        private double height;
        private double weight;
        private List<String> diseaseNames;
        private List<String> medications;
        private boolean isCooksAtHome;
    }

    @Getter
    @Builder
    public static class MyPageResponse {
        private String nickname;
        private String gender;
        private int age;
        private double height;
        private double weight;
        private List<String> diseaseNames;
        private List<String> medications;
        private boolean isCooksAtHome;
    }
}