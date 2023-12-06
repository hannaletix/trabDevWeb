package com.example.trabalhoDevWeb.dtos;

import jakarta.validation.constraints.NotBlank;

public record ProjectDto ( @NotBlank String name ) {
}
