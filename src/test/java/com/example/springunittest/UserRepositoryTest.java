package com.example.springunittest;

import com.example.springunittest.dto.UserAccount;
import com.example.springunittest.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class UserRepositoryTest {

    public static final String USER_ID = "testuser";
    public static final String HASHED_PASSWORD = "hashedpassword";

    private final UserAccount fakeUserAccount = new UserAccount(null, UUID.randomUUID(), USER_ID, HASHED_PASSWORD);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestHelper testHelper;

    @BeforeAll
    static void setUp() {
        TestDatabaseConfig.startContainer();
    }

    @BeforeEach
    void setUpEach() {
        testHelper.createTestTable();
        testHelper.clearDatabase();
    }

    @AfterAll
    static void tearDown(@Autowired TestHelper testHelper) {
        testHelper.clearDatabase();
    }

    @Test
    void testInsert() {
        var userCreatedResponse = userRepository.insert(fakeUserAccount);

        assertThat(userCreatedResponse)
                .isNotNull()
                .satisfies(inserted -> {
                    assertThat(inserted.uuid()).isEqualTo(fakeUserAccount.uuid());
                    assertThat(inserted.userId()).isEqualTo(fakeUserAccount.userId());
                });
    }

    @Test
    void testFindByUserIdNotFound() {
        boolean exist = userRepository.existsByUserId("nonexistent");
        assertThat(exist).isFalse();
    }

}
