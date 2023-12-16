package org.tommy.dev.studysupporter.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;

public record ApiResponse<T>(
    int code,
    HttpStatus status,
    String message,
    T data
) {

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {

        return new ApiResponse<>(httpStatus.value(), httpStatus, message, data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(long savedId) {

        return ResponseEntity.created(createdURI(savedId))
            .body(new ApiResponse<>(CREATED.value(), CREATED, "successfully created", null)
        );
    }

    private static URI createdURI(long id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();
    }
}
