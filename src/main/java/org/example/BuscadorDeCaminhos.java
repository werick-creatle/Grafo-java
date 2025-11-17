package org.example;

import java.util.*;

public class BuscadorDeCaminhos<T> {

    // Classe interna só pra guardar um vértice junto com a distância atual no cálculo.
    // Uso ela dentro do Dijkstra pra organizar a fila de prioridades.
    private static class NoDistancia<T> implements Comparable<NoDistancia<T>> {
        Vertice<T> vertice;
        int distancia;

        NoDistancia(Vertice<T> vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        // Comparo pela distância porque a fila de prioridade precisa saber quem vem primeiro.
        @Override
        public int compareTo(NoDistancia<T> outro) {
            return Integer.compare(this.distancia, outro.distancia);
        }
    }

    // Método principal: aqui eu encontro o melhor caminho e tento achar um alternativo também.
    public List<ResultadoCaminho<T>> encontrarCaminhos(Grafo<T> grafo, T inicio, T fim) {
        List<ResultadoCaminho<T>> caminhosEncontrados = new ArrayList<>();

        // Primeiro pego o caminho "oficial" com Dijkstra.
        ResultadoCaminho<T> caminho1 = dijkstra(grafo, inicio, fim);
        if (caminho1 == null) {
            return caminhosEncontrados;
        }
        caminhosEncontrados.add(caminho1);

        // Agora tento descobrir um caminho alternativo removendo arestas do original.
        List<ResultadoCaminho<T>> caminhosAlternativos = new ArrayList<>();
        List<T> trajetoCaminho1 = caminho1.getCaminho();

        // Aqui eu vou removendo cada aresta do caminho original pra ver se existe outro caminho válido.
        for (int i = 0; i < trajetoCaminho1.size() - 1; i++) {
            Vertice<T> u = grafo.getVertice(trajetoCaminho1.get(i));
            Vertice<T> v = grafo.getVertice(trajetoCaminho1.get(i + 1));

            // Removo a aresta nos dois sentidos (grafo não orientado).
            Aresta<T> arestaUV = u.removerArestaPara(v);
            Aresta<T> arestaVU = v.removerArestaPara(u);

            ResultadoCaminho<T> alternativo = dijkstra(grafo, inicio, fim);
            if (alternativo != null) {
                caminhosAlternativos.add(alternativo);
            }

            // Recoloco as arestas depois do teste.
            u.adicionarAresta(arestaUV);
            v.adicionarAresta(arestaVU);
        }

        // Escolho o melhor caminho alternativo, se tiver.
        if (!caminhosAlternativos.isEmpty()) {
            caminhosAlternativos.sort(Comparator.comparingInt(ResultadoCaminho::getDistancia));
            ResultadoCaminho<T> melhorAlternativo = caminhosAlternativos.get(0);

            // Adiciono só se realmente for diferente ou mais longo (evito duplicação do caminho principal).
            if (melhorAlternativo.getDistancia() > caminho1.getDistancia() ||
                    !melhorAlternativo.getCaminho().equals(caminho1.getCaminho())) {
                caminhosEncontrados.add(melhorAlternativo);
            }
        }

        return caminhosEncontrados;
    }

    // Implementação do Dijkstra: meu algoritmo pra achar o menor caminho entre dois pontos.
    private ResultadoCaminho<T> dijkstra(Grafo<T> grafo, T inicio, T fim) {
        grafo.limparVisitas();

        Vertice<T> vInicio = grafo.getVertice(inicio);
        Vertice<T> vFim = grafo.getVertice(fim);

        Map<Vertice<T>, Integer> distancias = new HashMap<>();
        PriorityQueue<NoDistancia<T>> fila = new PriorityQueue<>();

        // Inicio todo mundo com distância infinita.
        for (Vertice<T> v : grafo.getVertices()) {
            distancias.put(v, Integer.MAX_VALUE);
        }

        // O ponto inicial começa com distância zero.
        distancias.put(vInicio, 0);
        vInicio.setPredecessor(null);
        fila.add(new NoDistancia<>(vInicio, 0));

        // Aqui acontece o Dijkstra de verdade.
        while (!fila.isEmpty()) {
            NoDistancia<T> noAtual = fila.poll();
            Vertice<T> u = noAtual.vertice;

            if (u.isVisitado()) {
                continue;
            }
            u.setVisitado(true);

            // Se cheguei no ponto final, já posso parar.
            if (u.equals(vFim)) {
                break;
            }

            // Varro todas as arestas saindo desse vértice.
            for (Aresta<T> aresta : u.getArestasSaindo()) {
                Vertice<T> v = aresta.getDestino();
                if (!v.isVisitado()) {
                    int novaDistancia = distancias.get(u) + aresta.getPeso();

                    // Atualizo se achei um caminho menor.
                    if (novaDistancia < distancias.get(v)) {
                        distancias.put(v, novaDistancia);
                        v.setPredecessor(u);
                        fila.add(new NoDistancia<>(v, novaDistancia));
                    }
                }
            }
        }

        return reconstruirCaminho(vFim, distancias);
    }

    // Aqui eu remonto o caminho final usando os predecessores que o Dijkstra definiu.
    private ResultadoCaminho<T> reconstruirCaminho(Vertice<T> vFim, Map<Vertice<T>, Integer> distancias) {
        List<T> caminho = new ArrayList<>();
        Vertice<T> atual = vFim;

        // Se ainda está com distância infinita, quer dizer que nunca consegui chegar nele.
        if (distancias.get(vFim) == Integer.MAX_VALUE) {
            return null;
        }

        // Remonto o trajeto andando pelos predecessores.
        while (atual != null) {
            caminho.add(atual.getDado());
            atual = atual.getPredecessor();
        }

        Collections.reverse(caminho);

        return new ResultadoCaminho<>(caminho, distancias.get(vFim));
    }
}
