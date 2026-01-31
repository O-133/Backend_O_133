package com.example.demo.food.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "food_ingredient")
public class FoodIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId; // Placeholder until Ingredient entity is created/referenced

    public FoodIngredient(Food food, Integer ingredientId) {
        this.food = food;
        this.ingredientId = ingredientId;
    }
}
