package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record EpicDto(
        @NotBlank String titulo,
        String descricao,
        String relevancia,
        String categoria,
        @Valid long typeEpic_id,
        @Valid long project_id) {
}
