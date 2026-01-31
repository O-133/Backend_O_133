package com.example.demo.disease.repository;

import com.example.demo.disease.domain.DiseaseIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseIngredientRepository extends JpaRepository<DiseaseIngredient, Integer> {
}
