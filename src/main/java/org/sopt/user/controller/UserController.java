package org.sopt.user.controller;

import org.sopt.common.response.SuccessRes;
import org.sopt.user.dto.CreateUserReq;
import org.sopt.user.dto.CreateUserRes;
import org.sopt.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<SuccessRes<CreateUserRes>> createUser(@RequestBody final CreateUserReq createUserReq){

        CreateUserRes user = userService.createUser(createUserReq);

        HttpHeaders headers = new HttpHeaders();
        headers.set("userId", String.valueOf(user.userId()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(SuccessRes.created(user));
    }
}
