package com.example.springunittest;

import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Override
    public SignupResult signup(String userId, String password) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
