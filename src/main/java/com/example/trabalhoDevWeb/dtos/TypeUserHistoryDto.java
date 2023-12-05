package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;

public record TypeUserHistoryDto(@NotBlank String id, @NotBlank String descricao, @NotBlank String typeEpic_id) {
}
