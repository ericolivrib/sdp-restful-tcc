package br.com.erico.sdp.controller;

import br.com.erico.sdp.dto.ProjetoFromUsuarioDTO;
import br.com.erico.sdp.exception.DataLimiteExpiradaException;
import br.com.erico.sdp.model.Periodo;
import br.com.erico.sdp.model.Projeto;
import br.com.erico.sdp.service.DataLimiteService;
import br.com.erico.sdp.service.ProjetoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProjetoController {

    private final ProjetoService projetoService;
    private final DataLimiteService dataLimiteService;

    public ProjetoController(ProjetoService projetoService, DataLimiteService dataLimiteService) {
        this.projetoService = projetoService;
        this.dataLimiteService = dataLimiteService;
    }

    @Transactional
    @PostMapping(path = "/projetos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> adicionarProjeto(@RequestBody @Valid Projeto projeto, UriComponentsBuilder uriBuilder) {
        if (!dataLimiteService.isPeriodoVigente(Periodo.SUBMISSAO_PROJETOS)) {
            throw new DataLimiteExpiradaException(Periodo.SUBMISSAO_PROJETOS);
        }

        projetoService.adicionarProjeto(projeto);
        var projetoLocation = uriBuilder.path("/projetos/{id}").buildAndExpand(projeto.getId()).toUri();
        return ResponseEntity.created(projetoLocation).build();
    }

    @GetMapping(path = "/usuarios/{usuarioId}/projetos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ProjetoFromUsuarioDTO>>> getProjetosByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        var projetos = projetoService.getProjetosByUsuario(usuarioId);

        var projetosWithHypermidia = projetos.stream()
                .map(ProjetoFromUsuarioDTO::toEntityModel)
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));

        projetosWithHypermidia.add(linkTo(methodOn(ProjetoController.class).getProjetosByUsuario(usuarioId)).withSelfRel());

        if (projetos.isEmpty()) {
            projetosWithHypermidia.add(linkTo(methodOn(ProjetoController.class).adicionarProjeto(new Projeto(), null))
                    .withRel("adicionarProjeto"));
        }

        return ResponseEntity.ok(projetosWithHypermidia);
    }

    /* Outros métodos */

    @GetMapping("/projetos/{id}")
    public ResponseEntity<ProjetoFromUsuarioDTO> getProjetosById(@PathVariable("id") Long id) {
        ProjetoFromUsuarioDTO projeto = projetoService.getProjetoById(id);
        return ResponseEntity.ok(projeto);
    }

    @PutMapping("/projetos/{id}")
    public ResponseEntity<Void> atualizarProjeto(@PathVariable("id") Long projetoId, @RequestBody @Valid Projeto projeto) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/projetos/{id}")
    public ResponseEntity<Void> finalizarProjeto(@PathVariable("id") Long id) {
        projetoService.finalizarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable("id") Long projetoId) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projetos/{id}/relatorio")
    public ResponseEntity<byte[]> gerarRelatorio(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new byte[]{});
    }

}
