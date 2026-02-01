package com.example.demo.food.repository;

import com.example.demo.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Query("SELECT DISTINCT f FROM Food f " +
            "JOIN FETCH f.foodIngredients fi " +
            "JOIN FETCH fi.ingredient i " +
            "WHERE f.id IN (" +
            "    SELECT fi2.food.id FROM FoodIngredient fi2 " +
            "    JOIN UserDisease ud ON ud.user.id = :userId " +
            "    JOIN DiseaseIngredient di ON ud.disease.id = di.disease.id " +
            "    WHERE fi2.ingredient.id = di.ingredient.id" +
            ")")
    List<Food> findCandidateFoodsByDisease(@Param("userId") Integer userId);
}