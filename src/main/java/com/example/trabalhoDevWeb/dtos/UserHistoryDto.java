package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record UserHistoryDto(
        @NotBlank String titulo,
        String descricao,
        String relevancia,
        String categoria,
        @Valid long typeUserHistory_id,
        @Valid long epic_id) {
}
