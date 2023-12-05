package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;

public record EpicDto(
        @NotBlank String id,
        @NotBlank String titulo,
        String descricao,
        String relevancia,
        String categoria,
        @NotBlank String typeEpic_id) {
}
