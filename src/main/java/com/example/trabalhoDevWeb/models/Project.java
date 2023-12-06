package com.example.trabalhoDevWeb.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_PROJECTS")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
