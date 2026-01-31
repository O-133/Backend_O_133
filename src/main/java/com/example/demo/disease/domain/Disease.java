package com.example.demo.disease.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "disease")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL)
    private List<DiseaseIngredient> diseaseIngredients = new ArrayList<>();

    public Disease(String name) {
        this.name = name;
    }
}
