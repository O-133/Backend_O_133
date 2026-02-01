package com.example.demo.disease.repository.food.service;

import com.example.demo.disease.repository.DiseaseIngredientRepository;
import com.example.demo.disease.repository.food.domain.Food;
import com.example.demo.disease.repository.food.domain.FoodIngredient;
import com.example.demo.disease.repository.food.domain.Recipe;
import com.example.demo.disease.repository.food.dto.FoodDetails;
import com.example.demo.disease.repository.food.dto.FoodList;
import com.example.demo.disease.repository.food.dto.FoodRecipe;
import com.example.demo.disease.repository.food.repository.FoodIngredientRepository;
import com.example.demo.disease.repository.food.repository.FoodRepository;
import com.example.demo.disease.repository.food.repository.RecipeRepository;
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

    @Transactional(readOnly = true)
    public List<FoodList> getRecommendedFoods(Integer userId) {
        // 1. 유저의 질병 정보 및 해당 질병의 '추천 재료' 세트 조회
        UserDisease userDiseaseMapping = userDiseaseRepository.findByUserId(userId);
        if (userDiseaseMapping == null) return Collections.emptyList();

        // 질병에 도움이 되는 추천 재료 ID 리스트 확보
        Set<Integer> recommendedIngredientIds = diseaseIngredientRepository
                .findAllByDiseaseId(userDiseaseMapping.getDisease().getId())
                .stream()
                .map(di -> di.getIngredient().getId())
                .collect(Collectors.toSet());

        // 2. 냉장고 재료 조회 (보유/부족 구분용)
        Set<Integer> myIngredientIds = fridgeRepository.findAllByUserId(userId).stream()
                .map(f -> f.getIngredient().getId())
                .collect(Collectors.toSet());

        // 3. 음식 리스트 분석 및 변환
        return foodRepository.findAll().stream()
                .map(food -> {
                    List<Ingredient> foodIngredients = food.getFoodIngredients().stream()
                            .map(FoodIngredient::getIngredient)
                            .toList();

                    // [로직 A] 보유/부족 재료 분류
                    List<String> ownedNames = new ArrayList<>();
                    List<String> missingNames = new ArrayList<>();

                    // [로직 B] 질병 추천 재료 포함 여부 계산
                    long recommendedMatchCount = 0;

                    for (Ingredient ing : foodIngredients) {
                        // 냉장고 보유 체크
                        if (myIngredientIds.contains(ing.getId())) {
                            ownedNames.add(ing.getName());
                        } else {
                            missingNames.add(ing.getName());
                        }

                        // 질병 추천 재료 포함 여부 체크
                        if (recommendedIngredientIds.contains(ing.getId())) {
                            recommendedMatchCount++;
                        }
                    }

                    // 매칭률 계산: (음식 내 질병 추천 재료 수 / 해당 질병의 전체 추천 재료 수) * 100
                    // 만약 질병 추천 재료가 하나도 등록 안되어 있다면 0점 처리
                    double matchRate = recommendedIngredientIds.isEmpty() ? 0 :
                            (double) recommendedMatchCount / recommendedIngredientIds.size() * 100;

                    // 효과 리스트를 하나의 문자열로 결합 (예: "효과1, 효과2, 효과3")
                    String combinedEffect = String.join(", ", food.getEffect());

                    return FoodList.builder()
                            .id(food.getId())
                            .name(food.getName())
                            .difficulty(food.getDifficulty())
                            .time(food.getTime())
                            .matchPercentage(matchRate)
                            .ownedIngredients(ownedNames)
                            .missingIngredients(missingNames)
                            .effect(String.join(", ", food.getEffect())) // List를 String으로 결합
                            .healthNum(food.getHealthNum())
                            .calories(food.getCalories())
                            .profileImage(food.getProfile()) // 엔티티의 profile을 DTO의 profileImage에 매핑
                            .build();
                })
                // 4. 질병 추천 재료 매칭률 높은 순으로 정렬
                .sorted(Comparator.comparingDouble(FoodList::getMatchPercentage).reversed())
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public FoodDetails getFoodDetails(Integer userId, Integer foodId) {
        // 1. 진입 로그 (이게 안 찍히면 컨트롤러 매핑 문제)
        System.out.println("===> 서비스 진입: userId=" + userId + ", foodId=" + foodId);

        // 2. 음식 조회
        Food food = foodRepository.findById(foodId)
                .orElse(null);

        if (food == null) {
            System.out.println("===> 에러: DB에 해당 foodId(" + foodId + ")가 존재하지 않습니다.");
            return null;
        }

        // 3. 엔티티 데이터 출력 (여기서 null이 나오면 JPA 매핑 문제)
        System.out.println("===> 엔티티 확인: Name=" + food.getName() + ", Cal=" + food.getCalories());

        // 4. 질병/냉장고 데이터 조회 (간소화)
        UserDisease userDiseaseMapping = userDiseaseRepository.findByUserId(userId);
        Set<Integer> myIngredientIds = fridgeRepository.findAllByUserId(userId).stream()
                .map(f -> f.getIngredient().getId()).collect(Collectors.toSet());

        List<String> ownedNames = new ArrayList<>();
        List<String> missingNames = new ArrayList<>();

        // 5. 재료 매핑 로직
        for (FoodIngredient fi : food.getFoodIngredients()) {
            if (myIngredientIds.contains(fi.getIngredient().getId())) {
                ownedNames.add(fi.getIngredient().getName());
            } else {
                missingNames.add(fi.getIngredient().getName());
            }
        }

        // 6. DTO 생성 및 강제 출력
        FoodDetails details = new FoodDetails(
                food.getId(), food.getName(), food.getDifficulty(), food.getTime(),
                0.0, ownedNames, missingNames, food.getEffect(),
                food.getHealthNum(), food.getCalories(), food.getProfile()
        );

        System.out.println("===> 최종 DTO 매핑 완료: " + details.getName() + " (ID: " + details.getId() + ")");
        return details;
    }

    @Transactional(readOnly = true)
    public FoodRecipe getFoodRecipe(Integer foodId) {
        // food_id를 PK로 사용하는 recipe 테이블에서 context 조회
        String context = recipeRepository.findById(foodId)
                .map(Recipe::getContext)
                .orElseThrow(() -> new IllegalArgumentException("해당 음식의 레시피를 찾을 수 없습니다. id=" + foodId));

        // DTO 빌더를 사용하여 결과 반환 (줄바꿈 \n 포함됨)
        return FoodRecipe.builder()
                .recipe(context)
                .build();
    }
}
