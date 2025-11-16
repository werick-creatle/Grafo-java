package org.example;

import java.util.List;
import java.util.Scanner;

public class AppGrafo {

    public static void main(String[] args) {

        // Aqui eu monto o grafo inteiro antes de começar qualquer cálculo
        Grafo<String> grafo = new Grafo<>();
        popularGrafo(grafo);

        // Leitura básica pra pegar ponto inicial e final
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Calculadora de Rota - Jardim Taquaral ---");
        System.out.print("Digite o ponto de PARTIDA (Ex.: A): ");
        String partida = scanner.nextLine().toUpperCase();
        System.out.print("Digite o ponto de CHEGADA (Ex.: P): ");
        String chegada = scanner.nextLine().toUpperCase();

        // Verifico se os pontos existem no grafo antes de rodar Dijkstra
        if (grafo.getVertice(partida) == null || grafo.getVertice(chegada) == null) {
            System.err.println("Erro: Ponto de partida ou chegada inválido.");
            scanner.close();
            return;
        }

        // Aqui eu rodo meu buscador, que procura 1 ou 2 caminhos possíveis
        BuscadorDeCaminhos<String> buscador = new BuscadorDeCaminhos<>();
        List<ResultadoCaminho<String>> caminhos = buscador.encontrarCaminhos(grafo, partida, chegada);

        // Exibe o resultado pro usuário
        if (caminhos.isEmpty()) {
            System.out.println("\nNão foi possível encontrar um caminho de " + partida + " para " + chegada + ".");
        } else {
            System.out.println("\n--- Opções de Trajeto ---");

            // já estava no formato antigo, então deixei igual :)
            for (int i = 0; i < caminhos.size(); i++) {
                ResultadoCaminho<String> resultado = caminhos.get(i);
                System.out.println("\nOpção " + (i + 1) + " (Menor Caminho)");
                System.out.println("Trajeto: " + String.join(" -> ", resultado.getCaminho()));
                System.out.println("Distância Total: " + resultado.getDistancia() + " metros.");
            }
        }

        scanner.close();
    }

    // Aqui eu crio todos os pontos e conecto exatamente como no mapa
    private static void popularGrafo(Grafo<String> grafo) {

        // Primeiro adiciono todos os vértices
        String[] vertices = {
                "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "X"
        };

        // versão antiga do for
        for (int i = 0; i < vertices.length; i++) {
            String v = vertices[i];
            grafo.adicionaVertice(v);
        }

        // Agora conecto tudo — cada ligação adiciono nos dois sentidos,
        // já que meu grafo é basicamente um mapa real.
        adicionarArestaDupla(grafo, 300, "A", "B");
        adicionarArestaDupla(grafo, 370, "A", "S");
        adicionarArestaDupla(grafo, 317, "A", "X");
        adicionarArestaDupla(grafo, 47, "B", "C");
        adicionarArestaDupla(grafo, 62, "C", "D");
        adicionarArestaDupla(grafo, 141, "C", "H");
        adicionarArestaDupla(grafo, 8, "D", "E");
        adicionarArestaDupla(grafo, 13, "D", "F");
        adicionarArestaDupla(grafo, 230, "G", "I");
        adicionarArestaDupla(grafo, 138, "H", "I");
        adicionarArestaDupla(grafo, 153, "I", "J");
        adicionarArestaDupla(grafo, 512, "J", "K");
        adicionarArestaDupla(grafo, 135, "K", "L");
        adicionarArestaDupla(grafo, 167, "L", "N");

        // L ↔ M corrigido pra 50 metros
        adicionarArestaDupla(grafo, 50, "L", "M");

        adicionarArestaDupla(grafo, 108, "N", "O");
        adicionarArestaDupla(grafo, 82, "O", "P");
        adicionarArestaDupla(grafo, 215, "P", "Q");
        adicionarArestaDupla(grafo, 97, "Q", "R");
        adicionarArestaDupla(grafo, 33, "R", "S");
        adicionarArestaDupla(grafo, 243, "R", "T");
        adicionarArestaDupla(grafo, 38, "S", "V");
        adicionarArestaDupla(grafo, 22, "T", "U");
        adicionarArestaDupla(grafo, 207, "T", "V");
        adicionarArestaDupla(grafo, 210, "U", "V");
        adicionarArestaDupla(grafo, 107, "U", "X");
    }

    // Função pra facilitar a vida: adiciona aresta pros dois lados
    private static void adicionarArestaDupla(Grafo<String> g, int peso, String origem, String destino) {
        g.adicionaAresta(peso, origem, destino);
        g.adicionaAresta(peso, destino, origem);
    }
}
