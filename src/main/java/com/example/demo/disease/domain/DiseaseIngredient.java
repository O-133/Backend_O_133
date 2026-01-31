package com.example.demo.disease.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "disease_ingredient")
public class DiseaseIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id", nullable = false)
    private Disease disease;

    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;

    public DiseaseIngredient(Disease disease, Integer ingredientId) {
        this.disease = disease;
        this.ingredientId = ingredientId;
    }
}
