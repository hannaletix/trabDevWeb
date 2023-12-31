package com.example.trabalhoDevWeb.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_TASKS")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String descricao;
    private Long origem; // usado nos grafos
    private Long destino; // usado nos grafos
    @ManyToOne
    @JoinColumn(name = "typeTask_id")
    private TypeTask typeTask;
    @ManyToOne
    @JoinColumn(name = "userHistory_id")
    private UserHistory userHistory;

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

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

    public TypeTask getTypeTask() { return typeTask; }

    public void setTypeTask(TypeTask typeTask) { this.typeTask = typeTask; }

    public UserHistory getUserHistory() { return userHistory; }

    public void setUserHistory(UserHistory userHistory) { this.userHistory = userHistory; }

    public Long getOrigem() { return origem; }

    public void setOrigem(Long origem) { this.origem = origem; }

    public Long getDestino() { return destino; }

    public void setDestino(Long destino) { this.destino = destino; }
}
