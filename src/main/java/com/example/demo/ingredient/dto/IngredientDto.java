package com.example.demo.ingredient.dto;

import com.example.demo.ingredient.domain.Ingredient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class IngredientDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private String name;

        public Ingredient toEntity() {
            return new Ingredient(name);
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Integer id;
        private String name;

        public static Response from(Ingredient ingredient) {
            return Response.builder()
                    .id(ingredient.getId())
                    .name(ingredient.getName())
                    .build();
        }
    }
}
