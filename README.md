# 🗺️ Roteador de Bairro - ADO Estrutura de Dados

Este projeto é uma Atividade de Desenvolvimento (ADO) da disciplina de Estrutura de Dados, com o objetivo de aplicar conceitos de **Teoria dos Grafos** em um problema prático.

A aplicação, desenvolvida em **Java**, simula um sistema de rotas para o bairro Jardim Taquaral (Santo Amaro - SP). O mapa do bairro foi abstraído para um grafo, onde:

* **Vértices (Nós)**: Representam pontos de referência no bairro (A, B, C...).
* **Arestas (Pesos)**: Representam a distância em metros entre esses pontos.

## 🚀 Funcionalidades

O programa roda inteiramente no console e permite ao usuário:

1.  **Inserir um Ponto de Partida** (Ex: `A`).
2.  **Inserir um Ponto de Chegada** (Ex: `P`).

Com base nisso, o sistema calcula e exibe:

* **O Menor Caminho**: O trajeto ponto a ponto com a menor distância total em metros, utilizando o **Algoritmo de Dijkstra**.
* **Uma Rota Alternativa**: Apresenta o segundo menor caminho, caso exista.
* **A Distância Total** de cada trajeto.

## 🛠️ Tecnologias e Conceitos

* **Linguagem:** Java
* **Algoritmo:** Dijkstra
* **Conceitos:** Estrutura de Dados (Grafos, Vértices, Arestas), Listas de Adjacência, Fila de Prioridade (PriorityQueue).
