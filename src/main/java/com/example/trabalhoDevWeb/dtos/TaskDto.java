package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
public record TaskDto(
        @NotBlank String titulo,
        String descricao,
        @Valid long typeTask_id,
        @Valid long userHistory_id) {
}
