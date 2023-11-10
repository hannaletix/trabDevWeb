package com.example.trabalhoDevWeb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TB_USER_HISTORY")
public class UserHistoryModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String relevancia;
    private String categoria;
    @ManyToOne
    @JoinColumn(name = "typeUserHistory_id")
    private TypeUserHistoryModel typeUserHistory;
    @ManyToOne
    @JoinColumn(name = "epic_id")
    private EpicModel epic;
    @OneToMany(mappedBy = "userHistory")
    @JsonIgnore
    private List<TaskModel> task;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getRelevancia() { return relevancia; }

    public void setRelevancia(String relevancia) { this.relevancia = relevancia; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public TypeUserHistoryModel getTypeUserHistory() { return typeUserHistory; }

    public void setTypeUserHistory(TypeUserHistoryModel typeUserHistory) { this.typeUserHistory = typeUserHistory; }

    public EpicModel getEpic() { return epic; }

    public void setEpic(EpicModel epic) { this.epic = epic; }

    public List<TaskModel> getTask() { return task; }

    public void setTask(List<TaskModel> task) { this.task = task; }
}
