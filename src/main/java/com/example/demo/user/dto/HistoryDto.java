package com.example.demo.user.dto;

import com.example.demo.user.domain.History;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class HistoryDto {

    @Getter
    @Builder
    public static class Response {
        private Integer id;
        private Integer userId;
        private Integer foodId;
        private LocalDate createAt;

        public static Response from(History history) {
            return Response.builder()
                    .id(history.getId())
                    .userId(history.getUser().getId())
                    .foodId(history.getFoodId())
                    .createAt(history.getCreateAt())
                    .build();
        }
    }
}
