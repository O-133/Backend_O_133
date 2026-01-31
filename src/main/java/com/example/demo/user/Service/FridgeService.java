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

import java.util.ArrayList;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        for (String name : ingredientNames) {
            Ingredient ingredient = ingredientRepository.findByName(name)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료 이름입니다: " + name));

            if (!fridgeRepository.existsByUserIdAndIngredientName(userId, ingredient.getName())) {
                Fridge fridge = Fridge.builder()
                        .user(user)
                        .ingredient(ingredient)
                        .build();
                fridgeRepository.save(fridge);
            }
        }
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
    public List<FridgeDto.Info> addIngredientsByOcr(Integer userId, List<String> ocrLines) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 1. 우리가 관리하는 재료 목록 (계란, 양파, 돼지고기 등)
        List<Ingredient> masterIngredients = ingredientRepository.findAll();
        List<Fridge> addedFridges = new ArrayList<>();

        for (String line : ocrLines) {
            // 영수증 한 줄: "* 자색양파(1.5KG/망) 2,990 1 2,990"

            for (Ingredient master : masterIngredients) {
                String masterName = master.getName(); // 예: "양파"

                // 2. 해당 줄에 마스터 재료 이름이 포함되어 있는지 검사
                if (line.contains(masterName)) {

                    // 3. 중복 추가 방지 (이미 냉장고에 있는지 확인)
                    if (!fridgeRepository.existsByUserIdAndIngredientName(userId, master.getName())) {
                        Fridge fridge = Fridge.builder()
                                .user(user)
                                .ingredient(master)
                                .build();

                        addedFridges.add(fridgeRepository.save(fridge));
                    }
                    // 매칭되면 해당 줄 조사는 종료 (한 줄에 여러 재료가 나오지 않는다는 가정)
                    break;
                }
            }
        }

        return addedFridges.stream()
                .map(FridgeDto.Info::from)
                .toList();
    }

}