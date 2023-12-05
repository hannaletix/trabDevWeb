package com.example.trabalhoDevWeb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TB_TYPE_TASK")

public class TypeTask implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String descricao;

    @OneToMany(mappedBy = "typeTask")
    @JsonIgnore
    private List<Task> task;

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

    public List<Task> getTask() { return task; }

    public void setTask(List<Task> task) { this.task = task; }
}
