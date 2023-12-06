package com.example.trabalhoDevWeb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_USER_HISTORY")
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String descricao;
    private String relevancia;
    private String categoria;
    @ManyToOne
    @JoinColumn(name = "typeUserHistory_id")
    private TypeUserHistory typeUserHistory;
    @ManyToOne
    @JoinColumn(name = "epic_id")
    private Epic epic;
    @OneToMany(mappedBy = "userHistory")
    @JsonIgnore
    private List<Task> task;

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getRelevancia() { return relevancia; }

    public void setRelevancia(String relevancia) { this.relevancia = relevancia; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public TypeUserHistory getTypeUserHistory() { return typeUserHistory; }

    public void setTypeUserHistory(TypeUserHistory typeUserHistory) { this.typeUserHistory = typeUserHistory; }

    public Epic getEpic() { return epic; }

    public void setEpic(Epic epic) { this.epic = epic; }

    public List<Task> getTask() { return task; }

    public void setTask(List<Task> task) { this.task = task; }
}
