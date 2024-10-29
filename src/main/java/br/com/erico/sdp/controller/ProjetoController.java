package br.com.erico.sdp.controller;

import br.com.erico.sdp.dto.ProjetoRequest;
import br.com.erico.sdp.dto.ProjetoResponse;
import br.com.erico.sdp.exception.DataLimiteExpiradaException;
import br.com.erico.sdp.model.Periodo;
import br.com.erico.sdp.service.DataLimiteService;
import br.com.erico.sdp.service.ProjetoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    @PostMapping("/usuarios/{usuarioId}/projetos")
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

    @Transactional
    @GetMapping("/usuarios/{usuarioId}/projetos")
    public ResponseEntity<List<ProjetoResponse>> getProjetosByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        var projetos = projetoService.getProjetosByUsuario(usuarioId);
        return ResponseEntity.ok(projetos);
    }

}
