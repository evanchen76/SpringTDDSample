package com.example.springunittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void signup_ShouldFailWithInvalidInput() {
        var result = userService.signup("ab", "short");

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.error()).hasSize(2);
    }
}
