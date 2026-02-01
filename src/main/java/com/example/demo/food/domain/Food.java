package com.example.demo.food.domain;

import com.example.demo.disease.repository.food.domain.FoodIngredient; // 서비스와 동일한 경로 사용
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String difficulty;
    private String time;

    // 🔥 1, 3번 에러의 핵심: 이게 있어야 getFoodIngredients()를 부를 수 있습니다!
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodIngredient> foodIngredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "food_effects", joinColumns = @JoinColumn(name = "food_id"))
    private List<String> effect;

    private Integer healthNum;
    private Integer calories;
    private String profile;
}