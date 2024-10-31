package com.example.springunittest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SignupResult signup(String userId, String password) {
        //格式檢查
        List<String> signupValidations = new ArrayList<>();

        if (userId == null || userId.length() < 3 || userId.length() > 20) {
            signupValidations.add("User id must be between 3 and 20 characters long");
        }

        if (password == null || password.length() < 8 || password.length() > 30) {
            signupValidations.add("Password must be between 8 and 30 characters long");
        }

        //檢查帳號是否存在
        boolean exists = userRepository.existsByUserId(userId);
        if (exists) {
            return new SignupResult(null, List.of("User already exists"));
        }

        if (!signupValidations.isEmpty()){
            return new SignupResult(null, signupValidations);
        }else{
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
