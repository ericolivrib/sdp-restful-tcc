package br.com.erico.sdp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Usuário não autenticado")
public class UsuarioNaoAutenticadoException extends RuntimeException {

    public UsuarioNaoAutenticadoException() {
        super("Usuário não autenticado");
    }

}
