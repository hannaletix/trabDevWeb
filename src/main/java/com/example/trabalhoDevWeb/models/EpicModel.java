package com.example.trabalhoDevWeb.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_EPICS")

public class EpicModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "typeEpicId")
    private TypeEpicModel typeEpic;
    private String titulo;
    private String descricao;
    private String relevancia;
    private String categoria;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public TypeEpicModel getTypeEpic() { return typeEpic; }

    public void setTypeEpic(TypeEpicModel typeEpic) { this.typeEpic = typeEpic; }

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

    public String getRelevancia() { return relevancia; }

    public void setRelevancia(String relevancia) { this.relevancia = relevancia; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }
}
