package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;

public record TypeTaskDto(@NotBlank String id, @NotBlank String descricao) {
}
