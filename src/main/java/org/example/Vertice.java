package org.example;

import java.util.ArrayList;
import java.util.List;

public class Vertice<T> {

    // Aqui eu guardo o valor que esse vértice representa (pode ser nome, ID, etc.)
    private T dado;

    // Aqui ficam todas as arestas que saem desse vértice (ligações pra outros pontos)
    private List<Aresta<T>> arestasSaindo;

    // Flag pra eu saber se já passei por esse vértice durante a busca (Dijkstra)
    private boolean visitado = false;

    // Aqui eu guardo qual vértice veio antes desse no caminho (pra reconstruir o trajeto depois)
    private Vertice<T> predecessor = null;

    // Quando eu crio um vértice, já guardo o dado e deixo a lista de arestas pronta
    public Vertice(T dado) {
        this.dado = dado;
        this.arestasSaindo = new ArrayList<>();
    }

    // Crio uma nova aresta saindo daqui até o destino, com um peso
    public void adicionarAresta(int peso, Vertice<T> destino) {
        this.adicionarAresta(new Aresta<>(peso, destino));
    }

    // Removo uma aresta que leva até o vértice de destino
    public Aresta<T> removerArestaPara(Vertice<T> destino) {
        Aresta<T> arestaRemovida = null;

        for (Aresta<T> aresta : arestasSaindo) {
            if (aresta.getDestino().equals(destino)) {
                arestaRemovida = aresta;
                break; // Achei, posso sair do loop
            }
        }

        if (arestaRemovida != null) {
            arestasSaindo.remove(arestaRemovida);
        }

        return arestaRemovida;
    }

    // Adiciono uma aresta já pronta na lista
    public void adicionarAresta(Aresta<T> aresta) {
        if (aresta != null) {
            this.arestasSaindo.add(aresta);
        }
    }

    // Limpo o estado do vértice antes de rodar o Dijkstra de novo
    public void limparVisita() {
        this.visitado = false;
        this.predecessor = null;
    }

    // Retorno o valor que o vértice guarda
    public T getDado() {
        return dado;
    }

    // Retorno todas as arestas que saem daqui (usado nas buscas)
    public List<Aresta<T>> getArestasSaindo() {
        return arestasSaindo;
    }

    // Indica se esse vértice já foi visitado durante a busca
    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    // Retorno o vértice anterior no caminho encontrado
    public Vertice<T> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertice<T> predecessor) {
        this.predecessor = predecessor;
    }

    // Deixo o vértice fácil de identificar quando imprimir
    @Override
    public String toString() {
        return "Vertice{" + "dado=" + dado + '}';
    }
}
