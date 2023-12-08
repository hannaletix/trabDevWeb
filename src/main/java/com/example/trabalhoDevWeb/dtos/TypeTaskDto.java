package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record TypeTaskDto(@NotBlank String descricao, @Valid long typeUserHistory_id) {
}
