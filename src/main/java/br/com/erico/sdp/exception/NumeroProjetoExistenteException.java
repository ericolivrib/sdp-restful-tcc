package br.com.erico.sdp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NumeroProjetoExistenteException extends RuntimeException {

    public NumeroProjetoExistenteException(String numeroProjeto) {
        super("Já existem projetos associados ao número " + numeroProjeto);
    }

}
