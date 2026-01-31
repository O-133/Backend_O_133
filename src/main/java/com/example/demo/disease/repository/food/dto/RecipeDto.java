package com.example.demo.disease.repository.food.dto;

import com.example.demo.disease.repository.food.domain.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecipeDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private Integer foodId;
        private String context;
    }

    @Getter
    @Builder
    public static class Response {
        private Integer foodId;
        private String context;

        public static Response from(Recipe recipe) {
            return Response.builder()
                    .foodId(recipe.getFoodId())
                    .context(recipe.getContext())
                    .build();
        }
    }
}
