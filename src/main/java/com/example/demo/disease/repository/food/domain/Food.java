package com.example.demo.disease.repository.food.domain;

import com.example.demo.global.common.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10)
    private String difficulty;

    @Column(nullable = false, length = 20)
    private String time;

    @Column(nullable = false)
    private int healthNum;

    @Column(nullable = false)
    private int calories;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private List<String> effect = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String profile;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodIngredient> foodIngredients = new ArrayList<>();

    @Builder
    public Food(String name, String difficulty, String time, int healthNum, int calories, List<String> effect, String profile) {
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
        this.healthNum = healthNum;
        this.calories = calories;
        this.effect = (effect != null) ? effect : new ArrayList<>();
        this.profile = profile;
    }
}