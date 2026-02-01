package com.example.demo.disease.domain;

import com.example.demo.ingredient.domain.Ingredient; // Ingredient 임포트 잊지 마세요!
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

    // 수정: 단순 Integer에서 Ingredient 객체 매핑으로 변경
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    // 생성자도 변경된 객체에 맞게 수정
    public DiseaseIngredient(Disease disease, Ingredient ingredient) {
        this.disease = disease;
        this.ingredient = ingredient;
    }
}
