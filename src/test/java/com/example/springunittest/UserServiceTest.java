package com.example.springunittest;

import com.example.springunittest.dto.CreatedUser;
import com.example.springunittest.dto.UserAccount;
import com.example.springunittest.repository.UserRepository;
import com.example.springunittest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    private static final UUID USER_UUID = UUID.fromString("ff5230d0-d464-473c-a699-2953eb9c78ad");
    private final CreatedUser createdUser = new CreatedUser(USER_UUID, USER_ID);

    private static final String USER_ID = "testuser";
    private static final String PASSWORD = "password123";

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void signup_UsernameExists() {
        when(userRepository.existsByUserId(USER_ID)).thenReturn(true);
        var result = userService.signup(USER_ID, PASSWORD);

        assertFalse(result.isSuccess());
        assertThat(result.error()).hasSize(1);
    }

    @Test
    void signup_Success() {
        when(userRepository.existsByUserId(USER_ID)).thenReturn(false);
        when(userRepository.insert(any(UserAccount.class))).thenReturn(createdUser);

        var result = userService.signup(USER_ID, PASSWORD);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.createdUser()).isEqualTo(createdUser);
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
