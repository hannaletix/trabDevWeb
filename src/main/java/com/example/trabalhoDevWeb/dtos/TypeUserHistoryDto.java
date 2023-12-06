package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record TypeUserHistoryDto(@NotBlank String descricao, @Valid long typeEpic_id) {
}
