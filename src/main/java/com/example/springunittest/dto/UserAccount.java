package com.example.springunittest.dto;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

public record UserAccount(
        Long id,
        UUID uuid,
        @Field("user_id")
        String userId,
        String passwordHash
) {}

