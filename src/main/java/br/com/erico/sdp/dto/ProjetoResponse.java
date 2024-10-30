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

    public EntityModel<ProjetoResponse> toEntityModel() {
        var em = EntityModel.of(this);
        em.add(linkTo(methodOn(ProjetoController.class).getProjetosById(id)).withSelfRel());

        switch (status) {
            case NAO_FINALIZADO:
                em.add(linkTo(methodOn(ProjetoController.class).atualizarProjeto(id, null)).withRel("atualizarProjeto"));
                em.add(linkTo(methodOn(ProjetoController.class).deletarProjeto(id)).withRel("deletarProjeto"));
                em.add(linkTo(methodOn(ProjetoController.class).finalizarProjeto(id)).withRel("finalizarProjeto"));
                break;

            case NAO_AVALIADO:
                em.add(linkTo(methodOn(ProjetoController.class).gerarRelatorio(id)).withRel("relatorio"));
                break;
        }

        return em;
    }

    public static CollectionModel<EntityModel<ProjetoResponse>> toCollectionModel(List<ProjetoResponse> projetos,
                                                                                  Long usuarioId) {
        var cm = projetos.stream()
                .map(ProjetoResponse::toEntityModel)
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));

        cm.add(linkTo(methodOn(ProjetoController.class).getProjetosByUsuario(usuarioId)).withSelfRel());

        if (projetos.isEmpty()) {
            cm.add(linkTo(methodOn(ProjetoController.class).adicionarProjeto(null, usuarioId, null)).withRel("adicionarProjeto"));
        }

        return cm;
    }

}
