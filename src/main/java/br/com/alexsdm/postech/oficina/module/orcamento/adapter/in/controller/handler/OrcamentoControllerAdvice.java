package br.com.alexsdm.postech.oficina.module.orcamento.adapter.in.controller.handler;


import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrcamentoControllerAdvice {

    @ExceptionHandler(OrcamentoException.class)
    public ResponseEntity<ApiError> handleMyCustomException(OrcamentoException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrcamentoNaoEncontradaException.class)
    public ResponseEntity<ApiError> handleMyCustomException(OrcamentoNaoEncontradaException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMyCustomException(MethodArgumentNotValidException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}