package org.example;
import java.util.List;

public class ResultadoCaminho<T> {

    private List<T> caminho;
    private int distancia;

    public ResultadoCaminho(List<T> caminho, int distancia) {
        this.caminho = caminho;
        this.distancia = distancia;
    }

    public List<T> getCaminho() {
        return caminho;
    }

    public int getDistancia() {
        return distancia;
    }
}
