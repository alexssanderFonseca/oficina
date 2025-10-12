package br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.in.controller.handler;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception.PecaInsumoException;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception.PecaInsumoIndisponivelException;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PecaInsumoControllerAdvice {

    @ExceptionHandler(PecaInsumoException.class)
    public ResponseEntity<ApiError> handleMyCustomException(PecaInsumoException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PecaInsumoNaoEncontradaException.class)
    public ResponseEntity<ApiError> handleMyCustomException(PecaInsumoNaoEncontradaException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PecaInsumoIndisponivelException.class)
    public ResponseEntity<ApiError> handleMyCustomException(PecaInsumoIndisponivelException ex) {
        ApiError error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
