package br.com.erico.sdp.model;

import lombok.Getter;

@Getter
public enum Periodo {

    SUBMISSAO_PROJETOS(1, "Submissão de projetos"),
    ANALISE_PROJETOS(2, "Análise de projetos"),
    AJUSTE_PROJETOS(3, "Ajuste de projetos"),
    ANALISE_DESCRICAO(4, "Análise de descrição"),
    ANALISE_AJUSTES(5, "Análise de ajustes");

    private final int id;
    private final String nome;

    Periodo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
