package com.example.trabalhoDevWeb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "TB_TYPE_EPICS")

public class TypeEpicModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String descricao;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
