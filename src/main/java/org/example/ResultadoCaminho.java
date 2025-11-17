package org.example;

import java.util.List;

public class ResultadoCaminho<T> {

    // Aqui eu guardo o trajeto completo do caminho encontrado.
    // A lista vem na ordem certinha: início → fim.
    private final List<T> caminho;

    // E aqui eu guardo a distância total desse caminho.
    // Já vem calculada pelo Dijkstra.
    private final int distancia;

    // Construtor que recebe o trajeto e a distância final calculada.
    public ResultadoCaminho(List<T> caminho, int distancia) {
        this.caminho = caminho;
        this.distancia = distancia;
    }

    // Retorna a lista com o trajeto.
    public List<T> getCaminho() {
        return caminho;
    }

    // Retorna a distância total do caminho.
    public int getDistancia() {
        return distancia;
    }
}
