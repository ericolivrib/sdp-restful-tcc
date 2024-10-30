package br.com.erico.sdp.model;

import lombok.Getter;

@Getter
public enum StatusProjeto {

    NAO_FINALIZADO("Não Finalizado"),
    NAO_AVALIADO("Não Avaliado");

    private final String nome;

    StatusProjeto(String nome) {
        this.nome = nome;
    }

}
