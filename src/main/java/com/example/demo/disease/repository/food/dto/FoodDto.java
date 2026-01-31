package com.example.demo.disease.repository.food.dto;

import com.example.demo.disease.repository.food.domain.Food;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class  FoodDto {

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
}
