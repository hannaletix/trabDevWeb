package com.example.trabalhoDevWeb.services;

import com.example.trabalhoDevWeb.libGrafos.Aresta;
import com.example.trabalhoDevWeb.libGrafos.Grafo;
import com.example.trabalhoDevWeb.libGrafos.Vertice;
import com.example.trabalhoDevWeb.models.Epic;
import com.example.trabalhoDevWeb.models.Task;
import com.example.trabalhoDevWeb.models.UserHistory;
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
    public List<Long> ordenacaoTopologica() {
        List<Long> ordenacao = new ArrayList<>();
        Stack<Long> pilha = new Stack<>();
        List<Long> visitados = new ArrayList<>();

        for (Vertice<Long> vertice : this.grafo.getVertices()) {
            if (!visitados.contains(vertice.getDado())) {
                ordenacaoTopologicaRecursivo(vertice, visitados, pilha);
            }
        }

        // Adicionando à lista
        while (!pilha.isEmpty()) {
            ordenacao.add(pilha.pop());
        }

        return ordenacao;
    }

    // Método auxiliar recursivo para realizar a ordenação topológica a partir de um vértice
    private void ordenacaoTopologicaRecursivo(Vertice<Long> vertice, List<Long> visitados, Stack<Long> ordenacao) {
        visitados.add(vertice.getDado());

        for (Aresta<Long> aresta : vertice.getArestasSaida()) {
            // Obtém o vértice vizinho ao longo da aresta
            Vertice<Long> vizinho = aresta.getFim();
            if (!visitados.contains(vizinho.getDado())) {
                ordenacaoTopologicaRecursivo(vizinho, visitados, ordenacao);
            }
        }

        // Adiciona o vértice à pilha
        ordenacao.push(vertice.getDado());
    }
}

