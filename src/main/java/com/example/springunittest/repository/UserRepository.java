package com.example.springunittest.repository;

import com.example.springunittest.dto.CreatedUser;
import com.example.springunittest.dto.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean existsByUserId(String userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CreatedUser insert(UserAccount user) {
        String sql = "INSERT INTO user_account (uuid, user_id, password_hash) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql,
                user.uuid().toString(),
                user.userId(),
                user.passwordHash());
        if (rowsAffected != 1) {
            throw new RuntimeException("Failed to insert user account");
        }
        return new CreatedUser(user.uuid(), user.userId());
    }
}
