package com.example.demo.user.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller

public class UserController {

    @PostMapping("/users")
    public String createUser(@RequestParam UUID id, @RequestBody String name) {

        // 사용자 생성 로직
        return "userCreated";
    }
}
