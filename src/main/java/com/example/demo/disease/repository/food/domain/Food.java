package com.example.demo.disease.repository.food.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
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

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<FoodIngredient> foodIngredients = new ArrayList<>();

    public Food(String name, String difficulty, String time) {
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
    }
}
