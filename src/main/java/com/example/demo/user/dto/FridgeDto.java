package com.example.demo.user.dto;

import com.example.demo.user.domain.Fridge;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class FridgeDto {

    @Getter
    @Builder
    public static class Response {
        private Integer userId;
        private Integer ingredientId;
        private LocalDateTime createAt;

        public static Response from(Fridge fridge) {
            return Response.builder()
                    .userId(fridge.getId().getUserId())
                    .ingredientId(fridge.getId().getIngredientId())
                    .createAt(fridge.getCreateAt())
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED) //
    public static class AddRequest {
        private List<Integer> ingredientIds;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeleteRequest {
        private List<Integer> ingredientIds;
    }

    // 단건 재료 정보
    @Getter
    @Builder //
    public static class Info {
        private Integer ingredientId;
        private String ingredientName;
        private LocalDateTime createdAt;

        public static Info from(Fridge fridge) {
            return Info.builder()
                    .ingredientId(fridge.getId().getIngredientId())
                    .ingredientName(fridge.getIngredient().getName())
                    .createdAt(fridge.getCreateAt())
                    .build();
        }
    }

    // 전체 목록 응답용 Wrapper (선택 사항, List<Info>로 직접 반환 가능)
    @Getter
    @Builder
    public static class ListResponse {
        private List<Info> ingredients;
    }
}
