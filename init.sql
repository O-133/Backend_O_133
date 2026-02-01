SET NAMES 'utf8mb4';
SET FOREIGN_KEY_CHECKS = 0;
USE o133;

-- 1. 식재료 데이터 삽입
INSERT INTO ingredient (id, name) VALUES
                                      (1, '간장'), (2, '감자'), (3, '계란'), (4, '굴소스 약간'), (5, '느타리버섯'),
                                      (6, '단호박'), (7, '대구'), (8, '동태 살'), (9, '된장'), (10, '두부'),
                                      (11, '들기름'), (12, '들깨가루'), (13, '마'), (14, '멸치육수'), (15, '무'),
                                      (16, '물'), (17, '물 약간'), (18, '미나리'), (19, '미역'), (20, '브로콜리'),
                                      (21, '사과'), (22, '새우젓 약간'), (23, '소금'), (24, '숙주나물'), (25, '시금치'),
                                      (26, '쌀'), (27, '쌀가루'), (28, '애호박'), (29, '양념장'), (30, '양배추'),
                                      (31, '양파'), (32, '연근'), (33, '올리고당'), (34, '육수'), (35, '잘 익은 바나나'),
                                      (36, '저지방 우유'), (37, '참기름'), (38, '찹쌀가루'), (39, '청경채'), (40, '토란'), (41, '맥주효모');

-- 2. 질병 데이터 삽입
INSERT INTO disease (id, name) VALUES (1, '위염'), (2, '탈모 초기');
INSERT INTO user_disease (user_id, disease_id) VALUES (1, 1)
    ON DUPLICATE KEY UPDATE disease_id = VALUES(disease_id);

-- 3. 질병별 주의 성분 삽입
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES
                                                               (1, 2), (1, 4), (1, 5), (1, 8), (1, 10), (1, 35),
                                                               (2, 10), (2, 35), (2, 8), (2, 41);

-- 4. 음식 데이터 삽입 (기본 정보 + 효과 JSON + 이미지 URL 통합)
INSERT INTO food (id, name, difficulty, time, calories, health_num, effect, profile) VALUES
                                                                                         (1, '양배추 죽', '하', '20분', 360, 95, '["오메가-3가 LDL 콜레스테롤 감소에 기여합니다", "항염 지방산이 혈관 내 염증을 줄입니다", "단백질·채소 조합으로 과식 예방"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%EC%8A%A4%ED%81%AC%EB%9E%A8%EB%B8%94.png'),
                                                                                         (2, '마 반찬', '하', '5분', 330, 92, '["포화지방 섭취를 줄여 혈중 지방 개선", "열량 대비 단백질 비율이 높습니다", "가벼운 식단으로 심장 부담 완화"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%80%B4.png'),
                                                                                         (3, '브로콜리 미음', '하', '15분', 310, 93, '["식물성 단백질이 콜레스테롤 조절에 도움", "브로콜리 성분이 산화 스트레스 감소", "일상 식단으로 부담 적음"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%81%B4%EB%A6%AC%EC%96%B4%EC%B0%9C.png'),
                                                                                         (4, '감자 수프', '중', '20분', 420, 90, '["급격한 혈당·지질 변동 억제", "섬유질로 포만감 유지", "외식 대안으로 적합"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EC%95%A0%ED%98%B8%EB%B0%95%EC%A0%80%EB%8B%B9%EB%B3%B6%EC%9D%8C%EB%B0%A5.png'),
                                                                                         (5, '단호박 찜', '하', '15분', 280, 96, '["부드러운 질감으로 장 부담 최소화", "가스 생성 감소", "증상 심할 때 적합"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%EC%8A%A4%ED%81%AC%EB%9E%A8%EB%B8%94.png'),
                                                                                         (6, '무국', '하', '20분', 300, 93, '["자극성 재료 배제", "장 부담 없이 단백질 섭취", "가벼운 시작"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%80%B4.png'),
                                                                                         (7, '두부 데침', '하', '10분', 260, 95, '["수용성 섬유질 도움", "배변 패턴 개선", "증상 완화용"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%81%B4%EB%A6%AC%EC%96%B4%EC%B0%9C.png'),
                                                                                         (8, '연근 조림', '중', '30분', 240, 94, '["찜 조리로 위·장 부담 감소", "항산화 도움", "가벼운 마무리"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EC%95%A0%ED%98%B8%EB%B0%95%EC%A0%80%EB%8B%B9%EB%B3%B6%EC%9D%8C%EB%B0%A5.png'),
                                                                                         (9, '계란 찜', '하', '15분', 300, 97, '["양배추 비타민 U가 점막 회복 도움", "위산 자극 감소", "아침 식사 추천"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%EC%8A%A4%ED%81%AC%EB%9E%A8%EB%B8%94.png'),
                                                                                         (10, '대구 지리탕', '중', '30분', 330, 94, '["기름 사용 최소화", "근손실 예방", "위 자극 적음"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%80%B4.png'),
                                                                                         (11, '애호박 나물', '하', '10분', 280, 95, '["바나나가 위산 중화", "속쓰림 감소", "증상 심할 때"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%81%B4%EB%A6%AC%EC%96%B4%EC%B0%9C.png'),
                                                                                         (12, '바나나 퓨레', '하', '3분', 260, 96, '["위 체류 시간 짧음", "항산화 효과", "반복 섭취 가능"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EC%95%A0%ED%98%B8%EB%B0%95%EC%A0%80%EB%8B%B9%EB%B3%B6%EC%9D%8C%EB%B0%A5.png'),
                                                                                         (13, '청경채 볶음', '하', '10분', 260, 96, '["열량 대비 단백질 비율이 높아 체지방 감량에 유리합니다", "단백질과 수분이 포만감을 오래 유지합니다", "포화지방 섭취를 크게 줄입니다"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%EC%8A%A4%ED%81%AC%EB%9E%A8%EB%B8%94.png'),
                                                                                         (14, '흰살생선 찜', '중', '20분', 240, 95, '["식물성 단백질로 지방 부담이 적습니다", "체중 감량 중 대사 저하를 예방합니다", "위와 심장 부담이 적습니다"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%80%B4.png'),
                                                                                         (15, '찹쌀 미역국', '하', '25분', 300, 92, '["부드러운 질감으로 장 자극을 최소화합니다", "적은 열량으로도 배부름을 느낄 수 있습니다", "다이어트 중 설사·복통 위험 감소"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%81%B4%EB%A6%AC%EC%96%B4%EC%B0%9C.png'),
                                                                                         (16, '시금치 된장국', '하', '15분', 270, 94, '["수용성 섬유질이 장 운동을 돕습니다", "혈당과 포만감 균형이 좋습니다", "부담 없는 시작에 적합"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EC%95%A0%ED%98%B8%EB%B0%95%EC%A0%80%EB%8B%B9%EB%B3%B6%EC%9D%8C%EB%B0%A5.png'),
                                                                                         (17, '토란국', '중', '30분', 320, 95, '["양배추 성분이 위 점막 회복을 돕습니다", "위산 분비 자극을 줄입니다", "공복 부담 없이 섭취 가능"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%EC%8A%A4%ED%81%AC%EB%9E%A8%EB%B8%94.png'),
                                                                                         (18, '사과 소스', '하', '15분', 280, 93, '["바나나가 위산을 부드럽게 중화합니다", "저열량으로 감량 흐름 유지", "다이어트 중 위 통증 예방"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%91%90%EB%B6%80%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%80%B4.png'),
                                                                                         (19, '버섯 들깨탕', '중', '20분', 340, 92, '["양배추 성분이 위 점막 회복을 돕습니다", "위산 분비 자극을 줄입니다", "공복 부담 없이 섭취 가능"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EB%B8%8C%EB%A1%9C%EC%BD%9C%EB%A6%AC%ED%81%B4%EB%A6%AC%EC%96%B4%EC%B0%9C.png'),
                                                                                         (20, '숙주나물', '하', '10분', 220, 94, '["바나나가 위산을 부드럽게 중화합니다", "저열량으로 감량 흐름 유지", "다이어트 중 위 통증 예방"]', 'https://ap-northeast-2.console.aws.amazon.com/s3/object/o133-bucket?region=ap-northeast-2&prefix=%EB%B2%84%EC%84%AF%EC%95%A0%ED%98%B8%EB%B0%95%EC%A0%80%EB%8B%B9%EB%B3%B6%EC%9D%8C%EB%B0%A5.png');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES
                                                         (1, 30), (1, 26), (1, 16), (1, 1), (2, 13), (2, 1), (2, 37), (3, 20), (3, 27), (3, 34),
                                                         (4, 2), (4, 31), (4, 36), (5, 6), (6, 15), (6, 14), (6, 1), (7, 10), (7, 29), (8, 32),
                                                         (8, 1), (8, 33), (9, 3), (9, 16), (9, 22), (10, 7), (10, 15), (10, 18), (11, 28), (11, 11),
                                                         (11, 23), (12, 35), (13, 39), (13, 4), (14, 8), (14, 29), (15, 19), (15, 38), (15, 34), (16, 25),
                                                         (16, 9), (16, 34), (17, 40), (17, 12), (17, 34), (18, 21), (18, 17), (19, 5), (19, 12), (19, 34),
                                                         (20, 24), (20, 37), (20, 23);

-- 6. 레시피 데이터 삽입 (줄바꿈 TEXT)
INSERT INTO recipe (food_id, context) VALUES
                                          (1, '1. 연어 손질\n2. 양배추 찜\n3. 연어 스팀\n4. 플레이팅\n5. 올리브유 소량'),
                                          (2, '1. 닭가슴살 찢기\n2. 채소 손질\n3. 섞기\n4. 드레싱'),
                                          (3, '1. 두부 손질\n2. 브로콜리 볶기\n3. 두부 추가'),
                                          (4, '1. 현미 준비\n2. 닭가슴살 익히기\n3. 볼 구성'),
                                          (5, '1. 감자 삶기\n2. 으깨기\n3. 두부 섞기'),
                                          (6, '1. 채소 손질\n2. 볶기\n3. 달걀 추가'),
                                          (7, '1. 쌀 끓이기\n2. 바나나 으깨기'),
                                          (8, '1. 재료 손질\n2. 찜'),
                                          (9, '1. 채소 삶기\n2. 블렌딩'),
                                          (10, '1. 손질\n2. 찜'),
                                          (11, '1. 쌀죽\n2. 바나나 추가'),
                                          (12, '1. 찜\n2. 플레이팅'),
                                          (13, '1. 닭가슴살 찢기\n2. 오이 슬라이스\n3. 섞기\n4. 올리브유 소량'),
                                          (14, '1. 두부 손질\n2. 브로콜리 찜\n3. 담기\n4. 올리브유 몇 방울'),
                                          (15, '1. 채소 삶기\n2. 으깨기\n3. 물로 농도 조절'),
                                          (16, '1. 두부 준비\n2. 바나나 블렌딩\n3. 볼에 담기'),
                                          (17, '1. 채소 삶기\n2. 국물 위주로 섭취'),
                                          (18, '1. 두부 데치기\n2. 바나나 슬라이스\n3. 담기'),
                                          (19, '1. 채소 삶기\n2. 국물 위주로 섭취'),
                                          (20, '1. 두부 데치기\n2. 바나나 슬라이스\n3. 담기');

INSERT INTO user (id, name, age) VALUES (1, '테스트유저', 25)
    ON DUPLICATE KEY UPDATE id=id;

-- 냉장고 데이터 삽입 (UserId: 1)
-- 30: 양배추 (양배추 죽 재료)
-- 10: 두부 (두부 데침 재료)
-- 20: 브로콜리 (브로콜리 미음 재료)
-- 3: 계란 (계란 찜 재료)
INSERT INTO fridge (id, ingredient_id, create_at) VALUES
                                                      (1, 30, NOW()),
                                                      (1, 10, NOW()),
                                                      (1, 20, NOW()),
                                                      (1, 3, NOW());

SET FOREIGN_KEY_CHECKS = 1;