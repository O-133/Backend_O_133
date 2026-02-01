package com.example.demo.food.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FoodList {
    private Integer id;
    private String name;
    private String difficulty;
    private String time;
    private double matchPercentage;      // 매칭률 (%)
    private List<String> ownedIngredients;  // 유저가 가진 재료 중 음식에 포함된 것
    private List<String> missingIngredients; // 음식에 필요하지만 유저에게 없는 것
    private String effect;
    private Integer healthNum;
    private Integer calories;
    private String profileImage;
}