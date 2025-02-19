package com.example.springunittest.controller;

import com.example.springunittest.service.IUserService;
import com.example.springunittest.request.SignupRequest;
import com.example.springunittest.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ApiResponse<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        var signupResult = userService.signup(request.userId(), request.password());
        if (signupResult.isSuccess()) {
            var uuid = signupResult.createdUser().uuid();
            var userId = signupResult.createdUser().userId();

            return ApiResponse.success(new SignupResponse(uuid.toString(), userId));
        }else{
            return ApiResponse.fail(HttpStatus.BAD_REQUEST, signupResult.error().toString());
        }
    }

}