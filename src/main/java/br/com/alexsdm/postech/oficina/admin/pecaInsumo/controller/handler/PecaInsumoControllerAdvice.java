package br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.handler;


import br.com.alexsdm.postech.oficina.admin.pecaInsumo.exception.PecaInsumoException;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.exception.PecaInsumoIndisponivelException;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.exception.PecaInsumoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.commons.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMyCustomException(MethodArgumentNotValidException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



}
