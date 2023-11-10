package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserHistoryDto(
        @NotBlank String id,
        @NotBlank String titulo,
        String descricao,
        String relevancia,
        String categoria,
        @NotBlank String typeUserHistory_id,
        @NotBlank String epic_id) {
}
