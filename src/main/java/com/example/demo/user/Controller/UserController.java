package com.example.demo.user.Controller;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 게스트 사용자 등록 API
     * 닉네임, 성별, 나이, 신체정보, 질병명 리스트, 복용약 리스트를 입력받아 생성합니다.
     */
    @PostMapping("/guest")
    public ResponseEntity<UserDto.Response> createGuestUser(@RequestBody UserDto.CreateRequest request) {
        // 비즈니스 로직 수행 및 응답 DTO 반환
        UserDto.Response response = userService.join(request);

        // 생성 성공(201 Created) 상태와 함께 데이터 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}