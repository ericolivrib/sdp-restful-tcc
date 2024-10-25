package br.com.erico.sdp.dto;

import br.com.erico.sdp.model.EixoTecnologico;
import br.com.erico.sdp.model.Projeto;
import br.com.erico.sdp.model.StatusProjeto;
import br.com.erico.sdp.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjetoRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Número é obrigatório")
        @Size(min = 6, max = 6, message = "Número deve conter 6 caracteres")
        String numero,

        @NotBlank(message = "Modalidade é obrigatória")
        @Pattern(regexp = "(Ensino|Pesquisa|Extensão)", message = "Modalidade deve ser Ensino, Pesquisa ou Extensão")
        String modalidade,

        @NotBlank(message = "Justificativa é obrigatória")
        @Size(min = 20, max = 100, message = "Justificativa deve conter de 20 a 100 caracteres")
        String justificativa,

        @NotBlank(message = "Impactos ambientais são obrigatórios")
        @Size(min = 10, max = 100, message = "Impactos ambientais devem conter de 10 a 100 caracteres")
        String impactosAmbientais,

        @NotNull(message = "ID do eixo tecnológico é obrigatório")
        Long eixoTecnologicoId
) {

    public Projeto toEntity(Long usuarioId) {
        return Projeto.builder()
                .nome(nome)
                .numero(numero)
                .modalidade(modalidade)
                .justificativa(justificativa)
                .impactosAmbientais(impactosAmbientais)
                .eixoTecnologico(EixoTecnologico.builder().id(eixoTecnologicoId).build())
                .usuario(Usuario.builder().id(usuarioId).build())
                .dataCriacao(LocalDate.now())
                .ano(LocalDate.now().getYear())
                .status(StatusProjeto.NAO_FINALIZADO)
                .build();
    }

}
