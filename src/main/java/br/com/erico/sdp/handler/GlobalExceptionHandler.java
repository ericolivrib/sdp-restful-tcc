package br.com.erico.sdp.handler;

import br.com.erico.sdp.dto.ErrorMessageResponseDto;
import br.com.erico.sdp.dto.FieldErrorResponse;
import br.com.erico.sdp.exception.DataLimiteExpiradaException;
import br.com.erico.sdp.exception.NumeroProjetoExistenteException;
import br.com.erico.sdp.exception.UsuarioNaoAutenticadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorResponse>> handleBadRequest(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldErrorResponse::new)
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NumeroProjetoExistenteException.class)
    public ResponseEntity<ErrorMessageResponseDto> handleConflict(Exception ex) {

        /// Adiciona a mensagem de erro da exceção no DTO (Data Transfer Object) de erro.
        var error = new ErrorMessageResponseDto(ex.getMessage());

        /// Retorna o objeto de erro com um código de status 409 Conflict.
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }

    @ExceptionHandler(DataLimiteExpiradaException.class)
    public ResponseEntity<String> handlePreconditionFailed(Exception ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoAutenticadoException.class)
    public ResponseEntity<String> handleUnauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
    }

}
