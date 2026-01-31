package com.example.demo.disease.repository;

import com.example.demo.disease.domain.DiseaseIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface DiseaseIngredientRepository extends JpaRepository<DiseaseIngredient, Integer> {

    Arrays findAllByDiseaseIdIn(List<Integer> diseaseIds);
}
