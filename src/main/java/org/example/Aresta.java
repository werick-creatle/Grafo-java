package org.example;

public class Aresta<T> {

    // Aqui eu guardo o peso dessa ligação (quantos metros, custo, etc.)
    private int peso;

    // Aqui eu guardo pra qual vértice essa aresta está apontando
    private Vertice<T> destino;

    // Quando crio uma aresta, eu já digo qual é o peso e qual vértice ela alcança
    public Aresta(int peso, Vertice<T> destino) {
        this.peso = peso;
        this.destino = destino;
    }

    // Retorno o peso da aresta quando precisar calcular caminho
    public int getPeso() {
        return peso;
    }

    // Retorno o vértice de destino (pra saber pra onde essa ligação vai)
    public Vertice<T> getDestino() {
        return destino;
    }

    // Deixo a impressão da aresta mais legível no console
    @Override
    public String toString() {
        return "Aresta{" +
                "peso=" + peso +
                ", destino=" + destino.getDado() +
                '}';
    }
}
