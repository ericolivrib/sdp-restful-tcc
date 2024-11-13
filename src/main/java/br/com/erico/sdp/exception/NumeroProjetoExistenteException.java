package br.com.erico.sdp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Número de projeto já existente")
public class NumeroProjetoExistenteException extends RuntimeException {

    public NumeroProjetoExistenteException() {
        super("Número de projeto já existente");
    }

}
