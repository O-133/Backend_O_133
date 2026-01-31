package com.example.demo.user.Service;

import com.example.demo.user.dto.FridgeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClovaOcrService {

    @Value("${clova.ocr.secret-key}")
    private String secretKey;
    @Value("${clova.ocr.invoke-url}")
    private String invokeUrl;

    private final FridgeService fridgeService;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<FridgeDto.Info> processReceipt(Integer userId, MultipartFile file) {
        try {
            // 1. 헤더 설정 체크
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("X-OCR-SECRET", secretKey);

            // 2. 메타데이터 생성
            Map<String, Object> json = new HashMap<>();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());

            Map<String, String> image = new HashMap<>();
            image.put("format", getFileExtension(file));
            image.put("name", "receipt");
            json.put("images", List.of(image));

            String jsonString = new ObjectMapper().writeValueAsString(json);

            // 3. 바디 구성
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("message", jsonString);
            body.add("file", file.getResource());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            log.info("🚀 Clova API 호출 시작... URL: {}", invokeUrl);

            // 4. API 호출 및 상세 로그 출력
            ResponseEntity<String> response = restTemplate.postForEntity(invokeUrl, requestEntity, String.class);

            log.info("✅ Clova 응답 성공! HTTP Status: {}", response.getStatusCode());
            log.info("📝 응답 본문 원본: {}", response.getBody()); // 이 로그가 가장 중요합니다!

            // 5. JSON 파싱
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.getBody(), Map.class);
            List<String> extractedTexts = parseInferTexts(responseMap);

            return fridgeService.addIngredientsByOcr(userId, extractedTexts);

        } catch (org.springframework.web.client.HttpClientErrorException e) {
            log.error("❌ 클라이언트 에러 (4xx): {}, 바디: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Clova API 인증 또는 URL 오류: " + e.getMessage());
        } catch (Exception e) {
            log.error("❌ 시스템 에러 상세 스택트레이스: ", e); // 에러 위치를 정확히 찍어줌
            throw new RuntimeException("영수증 분석 실패: " + e.getMessage());
        }

    }

    private List<String> parseInferTexts(Map responseBody) {
        List<String> result = new ArrayList<>();
        try {
            List<Map<String, Object>> images = (List<Map<String, Object>>) responseBody.get("images");
            List<Map<String, Object>> fields = (List<Map<String, Object>>) images.get(0).get("fields");
            for (Map<String, Object> field : fields) {
                result.add((String) field.get("inferText"));
            }
        } catch (Exception e) {
            log.warn("⚠️ OCR 결과 파싱 중 에러: {}", e.getMessage());
        }
        return result;
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        return (name != null && name.contains(".")) ? name.substring(name.lastIndexOf(".") + 1) : "jpg";
    }
}