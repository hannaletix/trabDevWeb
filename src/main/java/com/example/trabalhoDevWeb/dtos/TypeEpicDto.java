package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;

public record TypeEpicDto(@NotBlank String descricao) {
}
