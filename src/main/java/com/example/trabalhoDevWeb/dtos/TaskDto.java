package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;
public record TaskDto(
        @NotBlank String titulo,
        String descricao,
        @NotBlank String typeTask_id,
        @NotBlank String userHistory_id) {
}
