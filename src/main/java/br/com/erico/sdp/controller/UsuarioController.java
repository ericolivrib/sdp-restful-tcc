package br.com.erico.sdp.controller;

import br.com.erico.sdp.model.Usuario;
import br.com.erico.sdp.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Void> adicionarUsuario(@RequestBody Usuario usuario, UriComponentsBuilder uriBuilder) {
        usuarioService.adicionarUsuario(usuario);
        var usuarioLocation = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(usuarioLocation).build();
    }

}
