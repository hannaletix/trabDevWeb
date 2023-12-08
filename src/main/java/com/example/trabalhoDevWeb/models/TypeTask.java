package com.example.trabalhoDevWeb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_TYPE_TASK")
public class TypeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;

    @OneToMany(mappedBy = "typeTask")
    @JsonIgnore
    private List<Task> task;

    @ManyToOne
    @JoinColumn(name = "typeUserHistory_id")
    private TypeUserHistory typeUserHistory;

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

    public List<Task> getTask() { return task; }

    public void setTask(List<Task> task) { this.task = task; }

    public TypeUserHistory getTypeUserHistory() { return typeUserHistory; }

    public void setTypeUserHistory(TypeUserHistory typeUserHistory) { this.typeUserHistory = typeUserHistory; }

}
