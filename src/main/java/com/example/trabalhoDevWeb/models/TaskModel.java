package com.example.trabalhoDevWeb.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_TASKS")

public class TaskModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String titulo;
    private String descricao;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
