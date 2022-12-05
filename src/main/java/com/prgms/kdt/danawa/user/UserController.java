package com.prgms.kdt.danawa.user;

import com.prgms.kdt.danawa.user.dto.UserLoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        userService.login(userLoginRequest);
        return ResponseEntity.ok("Login Success!");
    }
}
