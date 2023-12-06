package com.example.trabalhoDevWeb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "TB_TYPE_USER_HISTORY")
public class TypeUserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;
    @OneToMany(mappedBy = "typeUserHistory")
    @JsonIgnore
    private List<UserHistory> userHistory;
    @ManyToOne
    @JoinColumn(name = "typeEpic_id")
    private TypeEpic typeEpic;

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

    public List<UserHistory> getUserHistory() { return userHistory; }

    public void setUserHistory(List<UserHistory> userHistory) { this.userHistory = userHistory; }

    public TypeEpic getTypeEpic() { return typeEpic; }

    public void setTypeEpic(TypeEpic typeEpic) { this.typeEpic = typeEpic; }
}
