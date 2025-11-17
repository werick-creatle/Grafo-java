package org.example;

import java.util.ArrayList;
import java.util.List;

public class Grafo<T> {

    // Aqui eu guardo todos os vértices que existem no meu grafo
    // Cada vértice guarda um dado (como "A", "B", "C"...)
    private List<Vertice<T>> vertices;

    public Grafo() {
        // Quando o grafo nasce, já deixo a lista pronta pra receber os vértices
        this.vertices = new ArrayList<>();
    }

    // Aqui eu adiciono um novo dado como vértice do grafo
    // Antes eu verifico se esse vértice já existe pra não duplicar
    public void adicionaVertice(T dado) {
        if (getVertice(dado) == null) {
            // Se não existir ainda, crio um vértice novo com esse dado
            this.vertices.add(new Vertice<>(dado));
        }
    }

    // Aqui eu procuro um vértice pelo dado que ele carrega
    // É assim que eu acho os vértices quando vou criar conexões
    public Vertice<T> getVertice(T dado) {
        for (Vertice<T> v : vertices) {
            // Se o dado que eu quero bater com o dado do vértice, achei
            if (v.getDado().equals(dado)) {
                return v;
            }
        }
        // Se rodar tudo e não achar, retorno null mesmo
        return null;
    }

    // Aqui eu crio uma ligação entre dois vértices
    // O peso é a distância, e origem/destino são os pontos que quero ligar
    public void adicionaAresta(int peso, T dadoOrigem, T dadoDestino) {
        // Primeiro eu recupero os vértices reais dentro do grafo
        Vertice<T> origem = getVertice(dadoOrigem);
        Vertice<T> destino = getVertice(dadoDestino);

        // Só adiciono se os dois realmente existirem
        if (origem != null && destino != null) {
            origem.adicionarAresta(peso, destino);
        }
    }

    // Só retorno todos os vértices caso precise usar eles fora da classe
    public List<Vertice<T>> getVertices() {
        return this.vertices;
    }

    // Aqui eu reseto o estado dos vértices
    // isso é importante pra evitar lixo de buscas anteriores (em Dijkstra, BFS, etc.)
    public void limparVisitas() {
        for (Vertice<T> v : vertices) {
            v.limparVisita();
        }
    }
}
