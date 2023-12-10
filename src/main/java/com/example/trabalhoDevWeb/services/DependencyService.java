package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.libGrafos.Grafo;
import com.example.trabalhoDevWeb.libGrafos.Vertice;
import com.example.trabalhoDevWeb.models.Epic;
import com.example.trabalhoDevWeb.models.Task;
import com.example.trabalhoDevWeb.models.UserHistory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DependencyService {
    private Grafo<Long> grafo;  // Agora o grafo utiliza IDs para representar Épicos, Histórias de Usuário e Tarefas

    public DependencyService() {
        this.grafo = new Grafo<>();
    }

    public CompletableFuture<Void> addVerticeEpic(Epic epic) {
        // Adiciona o Épico ao grafo usando seu ID
        this.grafo.adicionarVertice(new Vertice<>(epic.getId()));

        return CompletableFuture.completedFuture(null);
    }

    public void addVerticeUserHistory(UserHistory userHistory) {
        // Adiciona a História de Usuário ao grafo usando seu ID
        this.grafo.adicionarVertice(new Vertice<>(userHistory.getId()));
    }

    public void addVerticeTask(Task task) {
        System.out.println("task.getId()" + task.getId());
        // Adiciona a Tarefa ao grafo usando seu ID
        this.grafo.adicionarVertice(new Vertice<>(task.getId()));

        System.out.println("inseriu:" + task.getId());
    }

    public void addDependencia(Long idOrigem, Long idDestino) {
        // Adiciona a dependência entre IDs ao grafo
        this.grafo.adicionarAresta(idOrigem, idDestino);
    }

    public boolean checkCycles() { return this.grafo.temCiclo(); }

    public void imprimirGrafo() { this.grafo.imprimirGrafo(); }
}

