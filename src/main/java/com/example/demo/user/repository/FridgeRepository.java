package com.example.demo.user.repository;

import com.example.demo.user.domain.Fridge;
import com.example.demo.user.domain.FridgeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, FridgeId> {
    boolean existsByUserIdAndIngredientName(Integer userId, String ingredientName);

    void deleteAllByUserIdAndIngredientNameIn(Integer userId, List<String> ingredientNames);

    List<Fridge> findAllByUserId(Integer userId);
}
