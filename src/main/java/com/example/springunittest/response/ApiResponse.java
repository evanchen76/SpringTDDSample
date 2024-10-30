package com.example.springunittest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<ApiResponse.Body<T>> {

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Body<T> {
        private final boolean success;
        private final T data;
        private final String error;

        private Body(boolean success, T data, String error) {
            this.success = success;
            this.data = data;
            this.error = error;
        }
    }

    private ApiResponse(HttpStatus status, boolean success, T data, String error) {
        super(new Body<>(success, data, error), status);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK, true, data, null);
    }

    public static <T> ApiResponse<T> fail(HttpStatus status, String error) {
        return new ApiResponse<>(status, false, null, error);
    }

    public static <T> ApiResponse<T> badRequest(String error) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, null, error);
    }

    public static <T> ApiResponse<T> notFound(String error) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND, false, null, error);
    }

    public static <T> ApiResponse<T> internalServerError(String error) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, false, null, error);
    }
}
