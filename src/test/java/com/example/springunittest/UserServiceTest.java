package com.example.springunittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    private static final String USER_ID = "testuser";
    private static final String PASSWORD = "password123";

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @ParameterizedTest
    @MethodSource("invalidSignupRequests")
    void signup_ShouldFailWithInvalidInput(String userId, String password, int expectedErrorCount) {
        var result = userService.signup(userId, password);

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.error()).hasSize(expectedErrorCount);
    }

    private static Stream<Arguments> invalidSignupRequests() {
        return Stream.of(
                // 測試無效的userId - 預期1個錯誤
                Arguments.of(null, PASSWORD, 1),
                Arguments.of("", PASSWORD, 1),
                Arguments.of("ab", PASSWORD, 1),
                Arguments.of("a".repeat(21), PASSWORD, 1),

                // 測試無效的password - 預期1個錯誤
                Arguments.of(USER_ID, null, 1),
                Arguments.of(USER_ID, "", 1),
                Arguments.of(USER_ID, "short", 1),
                Arguments.of(USER_ID, "a".repeat(31), 1),

                // 測試userId和password同時無效 - 預期2個錯誤
                Arguments.of("ab", "short", 2)
        );
    }
}
