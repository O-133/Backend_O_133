package com.example.demo.food.dto;

import com.example.demo.food.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FoodDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private String name;
        private String difficulty;
        private String time;

        public Food toEntity() {
            return new Food(name, difficulty, time);
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Integer id;
        private String name;
        private String difficulty;
        private String time;

        public static Response from(Food food) {
            return Response.builder()
                    .id(food.getId())
                    .name(food.getName())
                    .difficulty(food.getDifficulty())
                    .time(food.getTime())
                    .build();
        }
    }
    @Getter
    @AllArgsConstructor
    public static class FoodRecommendDto {
        private Food food;
        private double matchRate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class FoodRecommendResponse {
        private String foodName;
        private double matchRate;
        private String difficulty;
        private String time;
    }
}
