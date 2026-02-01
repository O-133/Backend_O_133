package com.example.demo.disease.repository.food.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder // 빌더를 추가하면 생성자 순서 실수를 방지할 수 있어 안전합니다.
public class FoodDetails {
    private Integer id;
    private String name;
    private String difficulty;
    private String time;
    private double matchPercentage;
    private List<String> ownedIngredients;
    private List<String> missingIngredients;
    private List<String> effects; // 서비스의 food.getEffect() 리스트를 담을 곳
    private Integer healthNum;
    private Integer calories;
    private String profileImage;
}