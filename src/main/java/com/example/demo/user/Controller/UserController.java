package com.example.demo.user.Controller;

import com.example.demo.global.api.ApiResponse;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<UserDto.Response> createGuestUser(@RequestBody UserDto.CreateRequest request) {
        UserDto.Response response = userService.join(request);
        return ApiResponse.ok(response);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserDto.MyPageResponse> getMyPage(@PathVariable Integer userId) {
        return ApiResponse.ok(userService.getMyPage(userId));
    }

    @PatchMapping("/{userId}")
    public ApiResponse<String> updateProfile(@PathVariable Integer userId,
                                             @RequestBody UserDto.UpdateRequest request) {
        userService.updateProfile(userId, request);
        return ApiResponse.ok("회원 정보가 수정되었습니다.");
    }
}