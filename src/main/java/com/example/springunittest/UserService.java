package com.example.springunittest;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
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

        if (!signupValidations.isEmpty()){
            return new SignupResult(null, signupValidations);
        }else{
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
