package com.example.springunittest.repository;

import com.example.springunittest.dto.CreatedUser;
import com.example.springunittest.dto.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    public Boolean existsByUserId(String userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CreatedUser insert(UserAccount user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
