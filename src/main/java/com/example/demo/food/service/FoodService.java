package com.example.demo.food.service;

import com.example.demo.disease.repository.DiseaseIngredientRepository;
import com.example.demo.disease.repository.food.domain.FoodIngredient;
import com.example.demo.disease.repository.food.domain.Recipe;
import com.example.demo.disease.repository.food.dto.FoodDetails;
import com.example.demo.disease.repository.food.dto.FoodList;
import com.example.demo.disease.repository.food.dto.FoodRecipe;
import com.example.demo.disease.repository.food.repository.FoodIngredientRepository;
import com.example.demo.food.repository.FoodRepository; // 올바른 repository 경로
import com.example.demo.disease.repository.food.repository.RecipeRepository;
import com.example.demo.food.domain.Food; // 엔티티 경로 통일
import com.example.demo.food.dto.FoodDto;
import com.example.demo.ingredient.domain.Ingredient;
import com.example.demo.user.domain.UserDisease;
import com.example.demo.user.repository.FridgeRepository;
import com.example.demo.user.repository.UserDiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final DiseaseIngredientRepository diseaseIngredientRepository;
    private final FoodIngredientRepository foodIngredientRepository;
    private final UserDiseaseRepository userDiseaseRepository;
    private final FridgeRepository fridgeRepository;
    private final RecipeRepository recipeRepository;

    /**
     * [1] 질병 적합도 기준 추천 (상단 로직)
     */
    @Transactional(readOnly = true)
    public List<FoodList> getRecommendedFoods(Integer userId) {
        UserDisease userDiseaseMapping = userDiseaseRepository.findByUserId(userId);
        if (userDiseaseMapping == null) return Collections.emptyList();

        // 질병 추천 재료 ID 셋
        Set<Integer> recommendedIngredientIds = diseaseIngredientRepository
                .findAllByDiseaseId(userDiseaseMapping.getDisease().getId())
                .stream()
                .map(di -> di.getIngredient().getId())
                .collect(Collectors.toSet());

        // 내 냉장고 재료 ID 셋
        Set<Integer> myIngredientIds = fridgeRepository.findAllByUserId(userId).stream()
                .map(f -> f.getIngredient().getId())
                .collect(Collectors.toSet());

        return foodRepository.findAll().stream()
                .map(food -> {
                    // 🔥 주의: 여기서 FoodIngredient 패키지 경로가 정확해야 함
                    List<Ingredient> foodIngredients = food.getFoodIngredients().stream()
                            .map(fi -> fi.getIngredient()) // 여기서 Ingredient를 제대로 추출하는지 확인
                            .toList();

                    List<String> ownedNames = new ArrayList<>();
                    List<String> missingNames = new ArrayList<>();
                    long recommendedMatchCount = 0;

                    for (Ingredient ing : foodIngredients) {
                        if (myIngredientIds.contains(ing.getId())) {
                            ownedNames.add(ing.getName());
                        } else {
                            missingNames.add(ing.getName());
                        }
                        if (recommendedIngredientIds.contains(ing.getId())) {
                            recommendedMatchCount++;
                        }
                    }

                    double matchRate = recommendedIngredientIds.isEmpty() ? 0 :
                            (double) recommendedMatchCount / recommendedIngredientIds.size() * 100;

                    return FoodList.builder()
                            .id(food.getId())
                            .name(food.getName())
                            .difficulty(food.getDifficulty())
                            .time(food.getTime())
                            .matchPercentage(matchRate)
                            .ownedIngredients(ownedNames)
                            .missingIngredients(missingNames)
                            // 엔티티가 List<String>이면 그대로, String이면 분리 로직 필요
                            .effect(food.getEffect() != null ? String.join(", ", food.getEffect()) : "")
                            .healthNum(food.getHealthNum())
                            .calories(food.getCalories())
                            .profileImage(food.getProfile())
                            .build();
                })
                .sorted(Comparator.comparingDouble(FoodList::getMatchPercentage).reversed())
                .toList();
    }

    /**
     * [2] 냉장고 재료 매핑율 기준 추천 (하단 로직)
     */
    @Transactional(readOnly = true)
    public List<FoodDto.FoodRecommendResponse> getRecommendedFoodsQuantity(Integer userId) {
        List<Integer> myFridgeIds = fridgeRepository.findAllByUserId(userId)
                .stream().map(f -> f.getIngredient().getId()).toList();

        if (myFridgeIds.isEmpty()) return Collections.emptyList();

        // Repository에 작성한 쿼리 호출
        List<Food> candidateFoods = foodRepository.findCandidateFoodsByDisease(userId);

        return candidateFoods.stream()
                .map(food -> {
                    long matchCount = food.getFoodIngredients().stream()
                            .filter(fi -> myFridgeIds.contains(fi.getIngredient().getId()))
                            .count();
                    double matchRate = (double) matchCount / food.getFoodIngredients().size() * 100;

                    return new FoodDto.FoodRecommendDto(food, matchRate);
                })
                .sorted(Comparator.comparing(FoodDto.FoodRecommendDto::getMatchRate).reversed())
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * [3] 음식 상세 정보 조회
     */
    // 3번 메서드 수정 부분
    @Transactional(readOnly = true)
    public FoodDetails getFoodDetails(Integer userId, Integer foodId) {
        // 🔥 엔티티 클래스 정확히 명시
        com.example.demo.food.domain.Food food = foodRepository.findById(foodId).orElse(null);
        if (food == null) return null;

        Set<Integer> myIngredientIds = fridgeRepository.findAllByUserId(userId).stream()
                .map(f -> f.getIngredient().getId()).collect(Collectors.toSet());

        List<String> ownedNames = new ArrayList<>();
        List<String> missingNames = new ArrayList<>();

        // 여기서 fi.getIngredient()가 null이 아닌지 체크가 필요할 수 있음
        for (var fi : food.getFoodIngredients()) {
            if (fi.getIngredient() != null) {
                if (myIngredientIds.contains(fi.getIngredient().getId())) {
                    ownedNames.add(fi.getIngredient().getName());
                } else {
                    missingNames.add(fi.getIngredient().getName());
                }
            }
        }

        return new FoodDetails(
                food.getId(),
                food.getName(),
                food.getDifficulty(),
                food.getTime(),
                0.0,
                ownedNames,
                missingNames,
                food.getEffect(), // DTO의 effects 필드와 타입(List<String>)이 맞는지 확인
                food.getHealthNum(),
                food.getCalories(),
                food.getProfile()
        );
    }

    /**
     * [4] 음식 레시피 조회
     */
    @Transactional(readOnly = true)
    public FoodRecipe getFoodRecipe(Integer foodId) {
        String context = recipeRepository.findById(foodId)
                .map(Recipe::getContext)
                .orElseThrow(() -> new IllegalArgumentException("해당 음식의 레시피를 찾을 수 없습니다. id=" + foodId));

        return FoodRecipe.builder()
                .recipe(context)
                .build();
    }

    private FoodDto.FoodRecommendResponse convertToResponse(FoodDto.FoodRecommendDto dto) {
        return FoodDto.FoodRecommendResponse.builder()
                .foodName(dto.getFood().getName())
                .matchRate(Math.round(dto.getMatchRate() * 10) / 10.0)
                .difficulty(dto.getFood().getDifficulty())
                .time(dto.getFood().getTime())
                .build();
    }
}