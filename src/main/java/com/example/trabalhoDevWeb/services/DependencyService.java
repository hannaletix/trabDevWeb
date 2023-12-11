package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.libGrafos.Aresta;
import com.example.trabalhoDevWeb.libGrafos.Grafo;
import com.example.trabalhoDevWeb.libGrafos.Vertice;
import com.example.trabalhoDevWeb.models.Epic;
import com.example.trabalhoDevWeb.models.Task;
import com.example.trabalhoDevWeb.models.UserHistory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
        // Adiciona a Tarefa ao grafo usando seu ID
        this.grafo.adicionarVertice(new Vertice<>(task.getId()));
    }

    public void addDependencia(Long idOrigem, Long idDestino) {
        // Adiciona a dependência entre IDs ao grafo
        this.grafo.adicionarAresta(idOrigem, idDestino);
    }

    public boolean checkCycles() { return this.grafo.temCiclo(); }

    public void imprimirGrafo() { this.grafo.imprimirGrafo(); }

    // Método para realizar a ordenação topológica do grafo
    public List<Long> ordenacaoTopologica() { return this.grafo.ordenacaoTopologica(); }

    @Transactional
    public void deleteAllTasks() {
        // Obtém todas as tarefas do grafo
        List<Vertice<Long>> verticesTarefas = grafo.getVertices();

        // Remove cada tarefa
        for (Vertice<Long> verticeTarefa : verticesTarefas) {
            grafo.removerVertice(verticeTarefa.getDado());
        }
    }

    @Transactional
    public void deleteAllUserHistories() {
        // Obtém todas as UserHistories do grafo
        List<Vertice<Long>> verticesUserHistories = grafo.getVertices();

        // Remove cada UserHistory
        for (Vertice<Long> verticeUserHistory : verticesUserHistories) {
            grafo.removerVertice(verticeUserHistory.getDado());
        }
    }

    @Transactional
    public void deleteAllEpics() {
        // Obtém todas as Epics do grafo
        List<Vertice<Long>> verticesEpics = grafo.getVertices();

        // Remove cada Epic
        for (Vertice<Long> verticeEpic : verticesEpics) {
            grafo.removerVertice(verticeEpic.getDado());
        }
    }
}

