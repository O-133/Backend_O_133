package com.example.demo.food.repository;

import com.example.demo.food.domain.FoodIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodIngredientRepository extends JpaRepository<FoodIngredient, Integer> {
}
