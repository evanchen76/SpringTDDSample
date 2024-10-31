package com.example.springunittest.service;

import com.example.springunittest.dto.SignupResult;

public interface IUserService {
    SignupResult signup(String userId, String password);
}
