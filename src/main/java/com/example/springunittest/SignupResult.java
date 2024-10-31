package com.example.springunittest;

import java.util.List;

public record SignupResult(CreatedUser createdUser, List<String> error) {
    public boolean isSuccess() {
        return error == null || error.isEmpty();
    }
}
