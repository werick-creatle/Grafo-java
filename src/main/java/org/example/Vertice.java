package org.example;

import java.util.ArrayList;
import java.util.List;

public class Vertice<T> {

    private T dado;
    private List<Aresta<T>> arestasSaindo;

    // Atributos que uso no Dijkstra
    private boolean visitado = false;
    private Vertice<T> predecessor = null;

    // Construtor: inicializo o dado e a lista de arestas que saem daqui
    public Vertice(T dado) {
        this.dado = dado;
        this.arestasSaindo = new ArrayList<>(); // preparo a lista de arestas
    }

    // Crio uma aresta a partir do peso e do vértice de destino e já adiciono na lista
    public void adicionarAresta(int peso, Vertice<T> destino) {
        this.adicionarAresta(new Aresta<>(peso, destino));
    }

    // Removo temporariamente a aresta que aponta pro vértice destino
    // Retorno a aresta removida pra eu poder recolocar depois
    public Aresta<T> removerArestaPara(Vertice<T> destino) {
        Aresta<T> arestaRemovida = null;

        // Percorro a lista manualmente pra achar a aresta certa
        for (int i = 0; i < arestasSaindo.size(); i++) {
            Aresta<T> aresta = arestasSaindo.get(i);

            // Se o destino bate, essa é a aresta que quero remover
            if (aresta.getDestino().equals(destino)) {
                arestaRemovida = aresta;
                break; // já achei, posso sair do loop
            }
        }

        // Se achei mesmo, tiro da lista
        if (arestaRemovida != null) {
            arestasSaindo.remove(arestaRemovida);
        }

        return arestaRemovida;
    }

    // Recoloco uma aresta já pronta na lista (uso isso pra devolver arestas removidas)
    public void adicionarAresta(Aresta<T> aresta) {
        if (aresta != null) {
            this.arestasSaindo.add(aresta);
        }
    }

    // Reseto o vértice para rodar outra busca: tiro a marcação de visitado e o predecessor
    public void limparVisita() {
        this.visitado = false;
        this.predecessor = null;
    }

    // Getters e setters
    public T getDado() {
        return dado;
    }

    public List<Aresta<T>> getArestasSaindo() {
        return arestasSaindo;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public Vertice<T> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertice<T> predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public String toString() {
        return "Vertice{" + "dado=" + dado + '}';
    }
}
