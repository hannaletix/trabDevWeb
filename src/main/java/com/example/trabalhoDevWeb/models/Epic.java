package com.example.trabalhoDevWeb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_EPICS")
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String descricao;
    private String relevancia;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "typeEpic_id")
    private TypeEpic typeEpic;

    @OneToMany(mappedBy = "epic")
    @JsonIgnore
    private List<UserHistory> userHistory;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

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

    public TypeEpic getTypeEpic() { return typeEpic; }

    public void setTypeEpic(TypeEpic typeEpic) { this.typeEpic = typeEpic; }

    public List<UserHistory> getUserHistory() { return userHistory; }

    public void setUserHistory(List<UserHistory> userHistory) { this.userHistory = userHistory; }

    public Project getProject() { return project; }

    public void setProject(Project project) { this.project = project; }
}
