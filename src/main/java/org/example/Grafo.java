package org.example;

import java.util.ArrayList;
import java.util.List;

public class Grafo<T> {

    // Aqui eu guardo todos os vértices que fazem parte do meu grafo
    private List<Vertice<T>> vertices;

    public Grafo() {
        this.vertices = new ArrayList<>();
    }

    // Aqui eu adiciono um novo vértice ao grafo
    public void adicionaVertice(T dado) {
        // Só adiciono se ele ainda não existir
        if (getVertice(dado) == null) {
            this.vertices.add(new Vertice<>(dado));
        }
    }

    // Esse método busca um vértice no grafo pelo seu dado
    public Vertice<T> getVertice(T dado) {
        for (int i = 0; i < vertices.size(); i++) {
            Vertice<T> v = vertices.get(i);

            // Aqui eu comparo o valor do vértice atual com o que eu quero encontrar
            if (v.getDado().equals(dado)) {
                return v;
            }
        }
        return null; // Se não achei, retorno null
    }

    // Aqui eu adiciono uma aresta entre dois vértices (direção única)
    public void adicionaAresta(int peso, T dadoOrigem, T dadoDestino) {
        Vertice<T> origem = getVertice(dadoOrigem);
        Vertice<T> destino = getVertice(dadoDestino);

        // Só adiciono se os dois vértices existirem no grafo
        if (origem != null && destino != null) {
            /*
            Aqui eu pego o vértice de origem e mando ele criar uma aresta apontando
            pro vértice de destino, com o peso informado.
            */
            origem.adicionarAresta(peso, destino);
        }
    }

    /*
    Esse método me entrega todos os vértices do meu grafo. Preciso dele porque,
    quando vou fazer algum algoritmo (ex: Dijkstra), eu uso ele pra percorrer todos os nós,
    inicializar distâncias, marcar quais ainda não foram visitados e montar a estrutura básica
    que o algoritmo precisa.
    */
    public List<Vertice<T>> getVertices() {
        return this.vertices;
    }

    // Aqui eu removo todas as marcações de "visitado" dos vértices
    public void limparVisitas() {
        for (int i = 0; i < vertices.size(); i++) {
            Vertice<T> v = vertices.get(i); // Aqui eu pego o vértice da vez
            v.limparVisita(); // E aqui eu limpo a visita pra resetar tudo
        }
    }
}
