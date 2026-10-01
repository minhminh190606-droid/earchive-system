package com.team5.earchive.dto;

public record FieldErrorResponse(
        String field,
        String message
) {
}