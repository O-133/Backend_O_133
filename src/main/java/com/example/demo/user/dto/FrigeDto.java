package com.example.demo.user.dto;

import com.example.demo.user.domain.Frige;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class FrigeDto {

    @Getter
    @Builder
    public static class Response {
        private Integer userId;
        private Integer ingredientId;
        private LocalDateTime createAt;

        public static Response from(Frige frige) {
            return Response.builder()
                    .userId(frige.getId().getUserId())
                    .ingredientId(frige.getId().getIngredientId())
                    .createAt(frige.getCreateAt())
                    .build();
        }
    }
}
