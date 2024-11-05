package br.com.erico.sdp.dto;

import org.springframework.validation.FieldError;

public record FieldErrorResponse(String campo, String mensagem) {

    public FieldErrorResponse(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
