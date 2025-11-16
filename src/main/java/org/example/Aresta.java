package org.example;

public class Aresta<T> {
    //Aqui eu guardo o peso dessa aresta
    private int peso;

    //Aqui eu guardo pra qual vértice essa aresta está apontando
    private Vertice<T> destino;

    //Construtor da aresta
    //Quando eu crio uma aresta, eu digo qual o peso dela e pra qual vértice ela vai
    public Aresta(int peso, Vertice<T> destino) {
        this.peso = peso;
        this.destino = destino;
    }

    //Retorno o peso dessa aresta
    public int getPeso() {
        return peso;
    }

    //Retorno o vértice de destino dessa aresta
    public Vertice<T> getDestino() {
        return destino;
    }

    //Aqui só deixo a aresta mais fácil de visualizar quando imprimir
    @Override
    public String toString() {
        return "Aresta{" +
                "peso=" + peso +
                ", destino=" + destino.getDado() +
                '}';
    }
}
