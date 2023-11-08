package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;
public record TaskDto(@NotBlank String id, @NotBlank String titulo, String descricao) {
}
