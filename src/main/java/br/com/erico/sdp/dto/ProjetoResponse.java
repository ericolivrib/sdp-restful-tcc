package br.com.erico.sdp.dto;

import br.com.erico.sdp.model.Projeto;
import br.com.erico.sdp.model.StatusProjeto;

import java.time.LocalDate;

public record ProjetoResponse(
        Long id,
        String nome,
        String numero,
        String modalidade,
        String justificativa,
        LocalDate dataCriacao,
        LocalDate dataFinalizacao,
        Integer ano,
        StatusProjeto status
) {

    public static ProjetoResponse fromEntity(Projeto projeto) {
        return new ProjetoResponse(
                projeto.getId(),
                projeto.getNome(),
                projeto.getNumero(),
                projeto.getModalidade(),
                projeto.getJustificativa(),
                projeto.getDataCriacao(),
                projeto.getDataFinalizacao(),
                projeto.getAno(),
                projeto.getStatus()
        );
    }

}
