package com.example.demo.disease.repository.food.repository;

import com.example.demo.disease.repository.food.domain.FoodIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodIngredientRepository extends JpaRepository<FoodIngredient, Integer> {
    @Query("SELECT fi FROM FoodIngredient fi JOIN FETCH fi.food JOIN FETCH fi.ingredient")
    List<FoodIngredient> findAllWithDetails();
}
