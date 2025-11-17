package org.example;

import java.util.List;
import java.util.Scanner;

public class AppGrafo {

    public static void main(String[] args) {

        // Aqui eu crio o grafo que vai representar o mapa do Taquaral
        Grafo<String> grafo = new Grafo<>();

        // Já preencho ele com todos os pontos e distâncias
        popularGrafo(grafo);

        // Leio do teclado o ponto de partida e o de chegada
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Calculadora de Rota - Jardim Taquaral ---");

        System.out.print("Digite o ponto de PARTIDA (Ex.: A): ");
        String partida = scanner.nextLine().toUpperCase();

        System.out.print("Digite o ponto de CHEGADA (Ex.: P): ");
        String chegada = scanner.nextLine().toUpperCase();

        // Se o usuário digitar letra que não existe no grafo, já paro tudo
        if (grafo.getVertice(partida) == null || grafo.getVertice(chegada) == null) {
            System.err.println("Erro: Ponto de partida ou chegada inválido.");
            scanner.close();
            return;
        }

        // Uso o buscador pra rodar o Dijkstra e tentar achar até dois trajetos
        BuscadorDeCaminhos<String> buscador = new BuscadorDeCaminhos<>();
        List<ResultadoCaminho<String>> caminhos = buscador.encontrarCaminhos(grafo, partida, chegada);

        // Se nem o menor caminho existir, aviso o usuário
        if (caminhos.isEmpty()) {
            System.out.println("\nNão foi possível encontrar um caminho de " + partida + " para " + chegada + ".");
        } else {
            System.out.println("\n--- Opções de Trajeto ---");

            // Aqui eu mostro cada caminho encontrado
            for (int i = 0; i < caminhos.size(); i++) {
                ResultadoCaminho<String> resultado = caminhos.get(i);

                System.out.println("\nOpção " + (i + 1) + " (Menor Caminho)");
                System.out.println("Trajeto: " + String.join(" -> ", resultado.getCaminho()));
                System.out.println("Distância Total: " + resultado.getDistancia() + " metros.");
            }
        }

        scanner.close();
    }

    // Aqui eu insiro todos os pontos do parque e todas as distâncias possíveis.
    // Basicamente construo o grafo inteiro pronto pra rodar o Dijkstra.
    private static void popularGrafo(Grafo<String> grafo) {

        // Primeiro adiciono todos os vértices (as letras que representam os pontos)
        String[] vertices = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X"
        };

        for (String v : vertices) {
            grafo.adicionaVertice(v);
        }

        // Depois adiciono as ligações (arestas) entre os pontos, sempre em mão dupla
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
        adicionarArestaDupla(grafo, 50, "L", "M"); // L ↔ M (essa foi sua correção original)
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

    // Essa função cria ligação nos dois sentidos — porque o grafo é não direcionado.
    private static void adicionarArestaDupla(Grafo<String> g, int peso, String origem, String destino) {
        g.adicionaAresta(peso, origem, destino);
        g.adicionaAresta(peso, destino, origem);
    }
}
