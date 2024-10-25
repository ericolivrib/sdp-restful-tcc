package br.com.erico.sdp.controller;

import br.com.erico.sdp.model.EixoTecnologico;
import br.com.erico.sdp.service.EixoTecnologicoService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class EixoTecnologicoController {

    private final EixoTecnologicoService eixoTecnologicoService;

    public EixoTecnologicoController(EixoTecnologicoService eixoTecnologicoService) {
        this.eixoTecnologicoService = eixoTecnologicoService;
    }

    @Transactional
    @PostMapping("/eixos-tecnologicos")
    public ResponseEntity<Void> adicionarEixoTecnologico(@RequestBody EixoTecnologico eixoTecnologico, UriComponentsBuilder uriBuilder) {
        eixoTecnologicoService.adicionarEixoTecnologico(eixoTecnologico);
        var eixoTecnologicoLocation = uriBuilder.path("/eixos-tecnologicos/{id}").buildAndExpand(eixoTecnologico.getId()).toUri();
        return ResponseEntity.created(eixoTecnologicoLocation).build();
    }

}
