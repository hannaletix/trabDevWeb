package com.example.trabalhoDevWeb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_TYPE_EPICS")

public class TypeEpic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;

    @OneToMany(mappedBy = "typeEpic")
    @JsonIgnore
    private List<Epic> epics;

    @OneToMany(mappedBy = "typeEpic")
    private List<TypeUserHistory> typeUserHistory;

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Epic> getEpics() { return epics; }

    public void setEpics(List<Epic> epics) { this.epics = epics; }
}
