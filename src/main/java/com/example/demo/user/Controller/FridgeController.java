package com.example.demo.user.Controller;

import com.example.demo.user.Service.ClovaOcrService;
import com.example.demo.global.api.ApiResponse;
import com.example.demo.user.Service.FridgeService;
import com.example.demo.user.dto.FridgeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;
    private final ClovaOcrService clovaOcrService;

    // 대량 추가
    @PostMapping("/{userId}")
    public ApiResponse<String> addIngredients(@PathVariable Integer userId,
                                              @RequestBody FridgeDto.AddRequest request) {
        fridgeService.addIngredients(userId, request.getIngredientNames());
        return ApiResponse.ok("선택한 재료들이 냉장고에 추가되었습니다.");
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<FridgeDto.Info>> getFridgeList(@PathVariable Integer userId) {
        // 모든 재료가 포함된 리스트 반환
        List<FridgeDto.Info> response = fridgeService.getFridgeList(userId);
        return ApiResponse.ok(response);
    }

    // 대량 삭제 (RequestBody를 받기 위해 별도 경로 지정)
    @PostMapping("/{userId}/delete")
    public ApiResponse<String> deleteIngredients(@PathVariable Integer userId,
                                                 @RequestBody FridgeDto.DeleteRequest request) {
        fridgeService.deleteIngredients(userId, request.getIngredientNames());
        return ApiResponse.ok("선택한 재료들이 삭제되었습니다.");
    }
    // 영수증 OCR 처리 및 재료 추가
    @PostMapping(value = "/{userId}/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<FridgeDto.Info>> processOcr(
            @PathVariable Integer userId,
            @RequestPart("file") MultipartFile file
    ) {
        List<FridgeDto.Info> result = clovaOcrService.processReceipt(userId, file);

        // 2. 최종적으로 저장된 재료 리스트 반환
        return ApiResponse.ok(result);
    }
}