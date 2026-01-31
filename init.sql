SET NAMES 'utf8mb4';
SET FOREIGN_KEY_CHECKS = 0;
USE o133;

-- 1. 식재료 데이터 삽입
INSERT INTO ingredient (id, name) VALUES (1, '간장');
INSERT INTO ingredient (id, name) VALUES (2, '감자');
INSERT INTO ingredient (id, name) VALUES (3, '계란');
INSERT INTO ingredient (id, name) VALUES (4, '굴소스 약간');
INSERT INTO ingredient (id, name) VALUES (5, '느타리버섯');
INSERT INTO ingredient (id, name) VALUES (6, '단호박');
INSERT INTO ingredient (id, name) VALUES (7, '대구');
INSERT INTO ingredient (id, name) VALUES (8, '동태 살');
INSERT INTO ingredient (id, name) VALUES (9, '된장');
INSERT INTO ingredient (id, name) VALUES (10, '두부');
INSERT INTO ingredient (id, name) VALUES (11, '들기름');
INSERT INTO ingredient (id, name) VALUES (12, '들깨가루');
INSERT INTO ingredient (id, name) VALUES (13, '마');
INSERT INTO ingredient (id, name) VALUES (14, '멸치육수');
INSERT INTO ingredient (id, name) VALUES (15, '무');
INSERT INTO ingredient (id, name) VALUES (16, '물');
INSERT INTO ingredient (id, name) VALUES (17, '물 약간');
INSERT INTO ingredient (id, name) VALUES (18, '미나리');
INSERT INTO ingredient (id, name) VALUES (19, '미역');
INSERT INTO ingredient (id, name) VALUES (20, '브로콜리');
INSERT INTO ingredient (id, name) VALUES (21, '사과');
INSERT INTO ingredient (id, name) VALUES (22, '새우젓 약간');
INSERT INTO ingredient (id, name) VALUES (23, '소금');
INSERT INTO ingredient (id, name) VALUES (24, '숙주나물');
INSERT INTO ingredient (id, name) VALUES (25, '시금치');
INSERT INTO ingredient (id, name) VALUES (26, '쌀');
INSERT INTO ingredient (id, name) VALUES (27, '쌀가루');
INSERT INTO ingredient (id, name) VALUES (28, '애호박');
INSERT INTO ingredient (id, name) VALUES (29, '양념장');
INSERT INTO ingredient (id, name) VALUES (30, '양배추');
INSERT INTO ingredient (id, name) VALUES (31, '양파');
INSERT INTO ingredient (id, name) VALUES (32, '연근');
INSERT INTO ingredient (id, name) VALUES (33, '올리고당');
INSERT INTO ingredient (id, name) VALUES (34, '육수');
INSERT INTO ingredient (id, name) VALUES (35, '잘 익은 바나나');
INSERT INTO ingredient (id, name) VALUES (36, '저지방 우유');
INSERT INTO ingredient (id, name) VALUES (37, '참기름');
INSERT INTO ingredient (id, name) VALUES (38, '찹쌀가루');
INSERT INTO ingredient (id, name) VALUES (39, '청경채');
INSERT INTO ingredient (id, name) VALUES (40, '토란');

-- 2. 음식 매핑 데이터 삽입
SET NAMES 'utf8mb4';
SET FOREIGN_KEY_CHECKS = 0;

-- 1. 양배추 죽
INSERT INTO food (id, name, difficulty, time) VALUES (1, '양배추 죽', '하', '20분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (1, 30), (1, 26), (1, 16), (1, 1);

-- 2. 마 반찬
INSERT INTO food (id, name, difficulty, time) VALUES (2, '마 반찬', '하', '5분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (2, 13), (2, 1), (2, 37);

-- 3. 브로콜리 미음
INSERT INTO food (id, name, difficulty, time) VALUES (3, '브로콜리 미음', '하', '15분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (3, 20), (3, 27), (3, 34);

-- 4. 감자 수프
INSERT INTO food (id, name, difficulty, time) VALUES (4, '감자 수프', '중', '20분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (4, 2), (4, 31), (4, 36);

-- 5. 단호박 찜
INSERT INTO food (id, name, difficulty, time) VALUES (5, '단호박 찜', '하', '15분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (5, 6);

-- 6. 무국
INSERT INTO food (id, name, difficulty, time) VALUES (6, '무국', '하', '20분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (6, 15), (6, 14), (6, 1);

-- 7. 두부 데침
INSERT INTO food (id, name, difficulty, time) VALUES (7, '두부 데침', '하', '10분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (7, 10), (7, 29);

-- 8. 연근 조림
INSERT INTO food (id, name, difficulty, time) VALUES (8, '연근 조림', '중', '30분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (8, 32), (8, 1), (8, 33);

-- 9. 계란 찜
INSERT INTO food (id, name, difficulty, time) VALUES (9, '계란 찜', '하', '15분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (9, 3), (9, 16), (9, 22);

-- 10. 대구 지리탕
INSERT INTO food (id, name, difficulty, time) VALUES (10, '대구 지리탕', '중', '30분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (10, 7), (10, 15), (10, 18);

-- 11. 애호박 나물
INSERT INTO food (id, name, difficulty, time) VALUES (11, '애호박 나물', '하', '10분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (11, 28), (11, 11), (11, 23);

-- 12. 바나나 퓨레
INSERT INTO food (id, name, difficulty, time) VALUES (12, '바나나 퓨레', '하', '3분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (12, 35);

-- 13. 청경채 볶음
INSERT INTO food (id, name, difficulty, time) VALUES (13, '청경채 볶음', '하', '10분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (13, 39), (13, 4);

-- 14. 흰살생선 찜
INSERT INTO food (id, name, difficulty, time) VALUES (14, '흰살생선 찜', '중', '20분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (14, 8), (14, 29);

-- 15. 찹쌀 미역국
INSERT INTO food (id, name, difficulty, time) VALUES (15, '찹쌀 미역국', '하', '25분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (15, 19), (15, 38), (15, 34);

-- 16. 시금치 된장국
INSERT INTO food (id, name, difficulty, time) VALUES (16, '시금치 된장국', '하', '15분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (16, 25), (16, 9), (16, 34);

-- 17. 토란국
INSERT INTO food (id, name, difficulty, time) VALUES (17, '토란국', '중', '30분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (17, 40), (17, 12), (17, 34);

-- 18. 사과 소스
INSERT INTO food (id, name, difficulty, time) VALUES (18, '사과 소스', '하', '15분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (18, 21), (18, 17);

-- 19. 버섯 들깨탕
INSERT INTO food (id, name, difficulty, time) VALUES (19, '버섯 들깨탕', '중', '20분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (19, 5), (19, 12), (19, 34);

-- 20. 숙주나물
INSERT INTO food (id, name, difficulty, time) VALUES (20, '숙주나물', '하', '10분');
INSERT INTO food_ingredient (food_id, ingredient_id) VALUES (20, 24), (20, 37), (20, 23);

INSERT INTO disease (id, name) VALUES (1, '위염');
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (1, 2);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (1, 4);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (1, 5);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (1, 8);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (1, 10);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (1, 35);

INSERT INTO disease (id, name) VALUES (2, '탈모 초기');
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (2, 10);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (2, 35);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (2, 8);
INSERT INTO disease_ingredient (disease_id, ingredient_id) VALUES (2, 41);



SET FOREIGN_KEY_CHECKS = 1;
SET FOREIGN_KEY_CHECKS = 1;