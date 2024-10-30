package br.com.erico.sdp.controller;

import br.com.erico.sdp.dto.ProjetoRequest;
import br.com.erico.sdp.dto.ProjetoResponse;
import br.com.erico.sdp.exception.DataLimiteExpiradaException;
import br.com.erico.sdp.model.Periodo;
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

import java.util.List;

@RestController
public class ProjetoController {

    private final ProjetoService projetoService;
    private final DataLimiteService dataLimiteService;

    public ProjetoController(ProjetoService projetoService, DataLimiteService dataLimiteService) {
        this.projetoService = projetoService;
        this.dataLimiteService = dataLimiteService;
    }

    @Transactional
    @PostMapping(path = "/usuarios/{usuarioId}/projetos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> adicionarProjeto(@RequestBody @Valid ProjetoRequest request,
                                                 @PathVariable("usuarioId") Long usuarioId,
                                                 UriComponentsBuilder uriBuilder) {
        if (!dataLimiteService.isPeriodoVigente(Periodo.SUBMISSAO_PROJETOS)) {
            throw new DataLimiteExpiradaException(Periodo.SUBMISSAO_PROJETOS);
        }

        Long projetoId = projetoService.adicionarProjeto(request, usuarioId);
        var projetoLocation = uriBuilder.path("/projetos/{id}").buildAndExpand(projetoId).toUri();
        return ResponseEntity.created(projetoLocation).build();
    }

    @GetMapping(path = "/usuarios/{usuarioId}/projetos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ProjetoResponse>>> getProjetosByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        var projetos = projetoService.getProjetosByUsuario(usuarioId);
        return ResponseEntity.ok(ProjetoResponse.toCollectionModel(projetos, usuarioId));
    }

    /* Outros métodos */

    @GetMapping("/projetos/{id}")
    public ResponseEntity<ProjetoResponse> getProjetosById(@PathVariable("id") Long id) {
        ProjetoResponse projeto = projetoService.getProjetoById(id);
        return ResponseEntity.ok(projeto);
    }

    @PutMapping("/projetos/{id}")
    public ResponseEntity<Void> atualizarProjeto(@PathVariable("id") Long projetoId, @RequestBody ProjetoRequest request) {
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
