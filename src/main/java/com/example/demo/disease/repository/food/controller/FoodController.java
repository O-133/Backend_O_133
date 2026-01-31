package com.example.demo.disease.repository.food.controller;

import com.example.demo.disease.repository.food.dto.FoodDetails;
import com.example.demo.disease.repository.food.dto.FoodList;
import com.example.demo.disease.repository.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<FoodList>> getSearch(@RequestParam("id") Integer id, @RequestParam("type") String type) {
        if (type.equals("disease")) {
            return ResponseEntity.status(HttpStatus.OK).body(foodService.getRecommendedFoods(id));
        }
        return null;
    }

    @GetMapping("/get_details")
    public ResponseEntity<FoodDetails> getDetails(@RequestParam("foodId") Integer foodId) {
        return null;
    }

}
