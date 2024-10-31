package com.example.springunittest.request;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank(message = "UserId cannot be empty")
        String userId,
        @NotBlank(message = "Password cannot be empty")
        String password) {
}
