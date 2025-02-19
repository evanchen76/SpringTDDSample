package com.example.springunittest;

import com.example.springunittest.controller.AuthController;
import com.example.springunittest.dto.CreatedUser;
import com.example.springunittest.dto.SignupResult;
import com.example.springunittest.request.SignupRequest;
import com.example.springunittest.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    @Test
    void signup_WhenAllFieldsEmpty_ShouldFailValidation() throws Exception {
        SignupRequest request = new SignupRequest("", "");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void signup_WhenSuccessful_ShouldReturnOkResponse() throws Exception {
        SignupRequest request = new SignupRequest(USER_ID, PASSWORD);
        when(userService.signup(request.userId(), request.password())).thenReturn(
                new SignupResult(new CreatedUser(UUID.randomUUID(), "userId"), null)
        );

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.uuid").value("uuid"))
                .andExpect(jsonPath("$.data.userId").value("userId"));
    }

    @Test
    void signup_WhenServiceReturnsFails_ShouldReturnBadRequest() throws Exception {
        SignupRequest request = new SignupRequest(USER_ID, PASSWORD);
        List<String> validationErrors = List.of("error1", "error2");
        when(userService.signup(request.userId(), request.password())).thenReturn(
                new SignupResult(null, validationErrors)
        );
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value(validationErrors.toString()));
    }

}
