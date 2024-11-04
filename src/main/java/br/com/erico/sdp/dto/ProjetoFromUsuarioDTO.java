package br.com.erico.sdp.dto;

import br.com.erico.sdp.controller.ProjetoController;
import br.com.erico.sdp.model.Projeto;
import br.com.erico.sdp.model.StatusProjeto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Relation(collectionRelation = "projetos")
public record ProjetoFromUsuarioDTO(
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

    public ProjetoFromUsuarioDTO(Projeto projeto) {
        this(projeto.getId(), projeto.getNome(), projeto.getNumero(), projeto.getModalidade(), projeto.getJustificativa(),
                projeto.getDataCriacao(), projeto.getDataFinalizacao(), projeto.getAno(), projeto.getStatus());
    }

    public EntityModel<ProjetoFromUsuarioDTO> toEntityModel() {
        var em = EntityModel.of(this);
        em.add(linkTo(methodOn(ProjetoController.class).getProjetosById(id)).withSelfRel());

        switch (status) {
            case NAO_FINALIZADO:
                em.add(linkTo(methodOn(ProjetoController.class).atualizarProjeto(id, new Projeto())).withRel("atualizarProjeto"));
                em.add(linkTo(methodOn(ProjetoController.class).deletarProjeto(id)).withRel("deletarProjeto"));
                em.add(linkTo(methodOn(ProjetoController.class).finalizarProjeto(id)).withRel("finalizarProjeto"));
                break;

            case NAO_AVALIADO:
                em.add(linkTo(methodOn(ProjetoController.class).gerarRelatorio(id)).withRel("relatorio"));
                break;
        }

        return em;
    }

}
