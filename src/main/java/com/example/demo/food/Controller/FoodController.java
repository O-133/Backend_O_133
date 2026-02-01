package com.example.demo.food.Controller;

import com.example.demo.food.dto.FoodDto;
import com.example.demo.food.dto.FoodList;
import com.example.demo.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/v1/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    // 1. 질병 적합도 기준 (상단 로직용)
    @GetMapping("/{userId}/recommend")
    public ResponseEntity<List<FoodList>> getRecommendations(@PathVariable Integer userId) {
        List<FoodList> responses = foodService.getRecommendedFoods(userId);
        return ResponseEntity.ok(responses);
    }

    // 2. 냉장고 재료 매핑율 기준 (하단 로직용)
    @GetMapping("/{userId}/recommendquantity") // foodId가 아니라 userId여야 냉장고를 뒤질 수 있습니다.
    public ResponseEntity<List<FoodDto.FoodRecommendResponse>> getRecommendQuantity(@PathVariable Integer userId) {
        List<FoodDto.FoodRecommendResponse> responses = foodService.getRecommendedFoodsQuantity(userId);
        return ResponseEntity.ok(responses);
    }
}