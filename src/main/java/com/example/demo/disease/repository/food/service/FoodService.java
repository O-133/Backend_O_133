package com.example.demo.disease.repository.food.service;

import com.example.demo.disease.repository.DiseaseIngredientRepository;
import com.example.demo.disease.repository.food.domain.FoodIngredient;
import com.example.demo.disease.repository.food.dto.FoodList;
import com.example.demo.disease.repository.food.repository.FoodIngredientRepository;
import com.example.demo.disease.repository.food.repository.FoodRepository;
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

    @Transactional(readOnly = true)
    public List<FoodList> getRecommendedFoods(Integer userId) {
        // userId로 diseaseRepository 조회해서 Set 형식으로 Disease name 불러옴 -> 단순히 userDisease 하나만 받아오면 돼 List 사용하지마
        // 질병은 반드시 1개만 존재함.
        // foodRepository에서 findAll 한 후 해당 정보로 해당 리스트에 많이 부합하는 음식들 리스트 출력 (내 냉장고에 있는 재료와 준비도 검사 % (보유 중인 재료, 부족한 재료 구분)
        // 부합하는 정도에 따라 정렬
        // FoodList 형식에 맞게 return
        UserDisease userDiseaseMapping = userDiseaseRepository.findByUserId(userId);
        String userDiseaseName = (userDiseaseMapping != null)
                ? userDiseaseMapping.getDisease().getName()
                : "없음";

        // 2. 냉장고 재료 조회 (재료 ID를 Set으로 보관하여 검색 속도 최적화)
        Set<Integer> myIngredientIds = fridgeRepository.findAllByUserId(userId).stream()
                .map(frige -> frige.getIngredient().getId())
                .collect(Collectors.toSet());

        // 3. 모든 음식을 순회하며 준비도 계산 및 정렬
        return foodRepository.findAll().stream()
                .map(food -> {
                    // 음식에 필요한 전체 재료 추출 (food_ingredient 테이블 활용)
                    List<Ingredient> requiredIngredients = food.getFoodIngredients().stream()
                            .map(FoodIngredient::getIngredient)
                            .toList();

                    List<String> ownedNames = new ArrayList<>();
                    List<String> missingNames = new ArrayList<>();

                    // 보유 여부에 따라 리스트 분리
                    for (Ingredient ing : requiredIngredients) {
                        if (myIngredientIds.contains(ing.getId())) {
                            ownedNames.add(ing.getName());
                        } else {
                            missingNames.add(ing.getName());
                        }
                    }

                    // 준비도(%) 계산
                    int totalCount = requiredIngredients.size();
                    int matchRate = (totalCount == 0) ? 0 : (ownedNames.size() * 100 / totalCount);

                    return FoodList.builder()
                            .id(food.getId())
                            .name(food.getName())
                            .difficulty(food.getDifficulty())
                            .time(food.getTime())
                            .matchPercentage(matchRate)
                            .ownedIngredients(ownedNames)
                            .missingIngredients(missingNames)
                            .build();
                })

                .sorted(Comparator.comparingDouble(FoodList::getMatchPercentage).reversed())
                .collect(Collectors.toList());
    }


}
