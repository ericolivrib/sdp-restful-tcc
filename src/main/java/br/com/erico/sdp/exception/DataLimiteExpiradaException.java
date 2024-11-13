package br.com.erico.sdp.exception;

import br.com.erico.sdp.model.Periodo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "Período expirado")
public class DataLimiteExpiradaException extends RuntimeException {

    public DataLimiteExpiradaException(Periodo periodo) {
        super("Período de " + periodo.getNome() + " expirado");
    }

}
