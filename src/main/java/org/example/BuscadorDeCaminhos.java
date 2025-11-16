package org.example;

import java.util.*;

public class BuscadorDeCaminhos<T> {

    // Essa classe aqui eu uso só pra fila do Dijkstra,
    // ela guarda o vértice e a distância pra eu conseguir comparar certinho.
    private static class NoDistancia<T> implements Comparable<NoDistancia<T>> {
        Vertice<T> vertice;
        int distancia;

        NoDistancia(Vertice<T> vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(NoDistancia<T> outro) {
            return Integer.compare(this.distancia, outro.distancia);
        }
    }

    // Aqui eu tento encontrar até dois caminhos diferentes entre dois pontos.
    public List<ResultadoCaminho<T>> encontrarCaminhos(Grafo<T> grafo, T inicio, T fim) {
        List<ResultadoCaminho<T>> caminhosEncontrados = new ArrayList<>();

        // Primeiro eu tento pegar o caminho padrão (o menor).
        ResultadoCaminho<T> caminho1 = dijkstra(grafo, inicio, fim);
        if (caminho1 == null) {
            return caminhosEncontrados; // Se nem caminho existe, já era.
        }
        caminhosEncontrados.add(caminho1);

        // Agora eu tento montar caminhos alternativos removendo arestas do caminho principal.
        List<ResultadoCaminho<T>> caminhosAlternativos = new ArrayList<>();
        List<T> trajetoCaminho1 = caminho1.getCaminho();

        // Aqui eu percorro o caminho principal inteiro, pegando pares de vértices.
        for (int i = 0; i < trajetoCaminho1.size() - 1; i++) {

            Vertice<T> u = grafo.getVertice(trajetoCaminho1.get(i));
            Vertice<T> v = grafo.getVertice(trajetoCaminho1.get(i + 1));

            // Removo temporariamente a aresta dos dois lados pra forçar outras rotas.
            Aresta<T> arestaUV = u.removerArestaPara(v);
            Aresta<T> arestaVU = v.removerArestaPara(u);

            // Depois de tirar a aresta, tento rodar o Dijkstra de novo pra descobrir outro caminho.
            ResultadoCaminho<T> alternativo = dijkstra(grafo, inicio, fim);
            if (alternativo != null) {
                caminhosAlternativos.add(alternativo);
            }

            // E agora eu devolvo tudo pro lugar pra não ferrar o grafo.
            u.adicionarAresta(arestaUV);
            v.adicionarAresta(arestaVU);
        }

        // Aqui eu escolho o melhor caminho alternativo, se tiver pelo menos um.
        if (!caminhosAlternativos.isEmpty()) {

            // Convertido para for normal
            caminhosAlternativos.sort(new Comparator<ResultadoCaminho<T>>() {
                @Override
                public int compare(ResultadoCaminho<T> o1, ResultadoCaminho<T> o2) {
                    return Integer.compare(o1.getDistancia(), o2.getDistancia());
                }
            });

            ResultadoCaminho<T> melhorAlternativo = caminhosAlternativos.get(0);

            // Só coloco se realmente for diferente do principal.
            if (melhorAlternativo.getDistancia() > caminho1.getDistancia() ||
                    !melhorAlternativo.getCaminho().equals(caminho1.getCaminho())) {
                caminhosEncontrados.add(melhorAlternativo);
            }
        }

        return caminhosEncontrados;
    }

    // Aqui é o Dijkstra tradicional que calcula o menor caminho.
    private ResultadoCaminho<T> dijkstra(Grafo<T> grafo, T inicio, T fim) {

        grafo.limparVisitas();

        Vertice<T> vInicio = grafo.getVertice(inicio);
        Vertice<T> vFim = grafo.getVertice(fim);

        Map<Vertice<T>, Integer> distancias = new HashMap<>();
        PriorityQueue<NoDistancia<T>> fila = new PriorityQueue<>();

        // Aqui eu inicializo todas as distâncias com infinito, menos o início.
        List<Vertice<T>> listaVertices = grafo.getVertices();
        for (int i = 0; i < listaVertices.size(); i++) {
            Vertice<T> v = listaVertices.get(i);
            distancias.put(v, Integer.MAX_VALUE);
        }

        distancias.put(vInicio, 0);
        vInicio.setPredecessor(null);
        fila.add(new NoDistancia<>(vInicio, 0));

        // Loop principal do Dijkstra.
        while (!fila.isEmpty()) {
            NoDistancia<T> noAtual = fila.poll();
            Vertice<T> u = noAtual.vertice;

            if (u.isVisitado()) {
                continue;
            }
            u.setVisitado(true);

            if (u.equals(vFim)) {
                break; // Já cheguei no destino.
            }

            // Relaxamento das arestas.
            List<Aresta<T>> arestas = u.getArestasSaindo();
            for (int i = 0; i < arestas.size(); i++) {
                Aresta<T> aresta = arestas.get(i);
                Vertice<T> v = aresta.getDestino();

                if (!v.isVisitado()) {
                    int novaDistancia = distancias.get(u) + aresta.getPeso();

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

    // Aqui eu reconstruo o caminho final usando os predecessores.
    private ResultadoCaminho<T> reconstruirCaminho(Vertice<T> vFim, Map<Vertice<T>, Integer> distancias) {
        List<T> caminho = new ArrayList<>();
        Vertice<T> atual = vFim;

        if (distancias.get(vFim) == Integer.MAX_VALUE) {
            return null; // Não tem caminho.
        }

        // Subo pelos predecessores até chegar no início.
        while (atual != null) {
            caminho.add(atual.getDado());
            atual = atual.getPredecessor();
        }

        // Como eu fui do fim pro início, preciso inverter.
        Collections.reverse(caminho);

        return new ResultadoCaminho<>(caminho, distancias.get(vFim));
    }
}
