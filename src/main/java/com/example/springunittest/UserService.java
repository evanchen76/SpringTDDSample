package com.example.springunittest;

import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Override
    public SignupResult signup(SignupRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
