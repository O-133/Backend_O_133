package com.example.demo.user.Service;

import com.example.demo.ingredient.domain.Ingredient;
import com.example.demo.ingredient.repository.IngredientRepository;
import com.example.demo.user.domain.Fridge;
import com.example.demo.user.domain.User;
import com.example.demo.user.dto.FridgeDto;
import com.example.demo.user.repository.FridgeRepository;
import com.example.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    /**
     * 냉장고 재료 추가
     */
    @Transactional
    public void addIngredients(Integer userId, List<String> ingredientNames) {
        //todo:
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
//
//        for (Integer ingredientId : ingredientIds) {
//            Ingredient ingredient = ingredientRepository.findById(ingredientId)
//                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료 ID: " + ingredientId));
//
//            // 중복 추가 방지 로직 (선택 사항)
//            if (!fridgeRepository.existsByUserIdAndIngredientId(userId, ingredientId)) {
//                Fridge fridge = Fridge.builder()
//                        .user(user)
//                        .ingredient(ingredient)
//                        .build();
//                fridgeRepository.save(fridge);
//            }
//        }
    }

    /**
     * 냉장고 재료 삭제
     */
    @Transactional
    public void deleteIngredients(Integer userId, List<String> ingredientNames) {
        fridgeRepository.deleteAllByUserIdAndIngredientNameIn(userId, ingredientNames);
    }

    public List<FridgeDto.Info> getFridgeList(Integer userId) {
        List<Fridge> fridges = fridgeRepository.findAllByUserId(userId);

        return fridges.stream()
                .map(FridgeDto.Info::from)
                .toList();
    }
    @Transactional
    public void addIngredientsByOcr(Integer userId, List<String> ocrTexts) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Ingredient> masterIngredients = ingredientRepository.findAll();

        for (String text : ocrTexts) {
            masterIngredients.stream()
                    .filter(master -> text.contains(master.getName()))
                    .findFirst()
                    .ifPresent(matchedIngredient -> {
                        boolean exists = fridgeRepository.existsByUserIdAndIngredientName(
                                userId, matchedIngredient.getName());

                        if (!exists) {
                            fridgeRepository.save(Fridge.builder()
                                    .user(user)
                                    .ingredient(matchedIngredient)
                                    .build());
                        }
                    });
        }
    }

}