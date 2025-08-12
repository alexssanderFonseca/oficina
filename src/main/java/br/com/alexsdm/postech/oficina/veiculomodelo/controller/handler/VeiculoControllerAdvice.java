package br.com.alexsdm.postech.oficina.veiculomodelo.controller.handler;


import br.com.alexsdm.postech.oficina.commons.ApiError;
import br.com.alexsdm.postech.oficina.veiculomodelo.exception.VeiculoModeloNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VeiculoControllerAdvice {

    @ExceptionHandler(VeiculoModeloNaoEncontradoException.class)
    public ResponseEntity<ApiError> handleMyCustomException(VeiculoModeloNaoEncontradoException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMyCustomException(MethodArgumentNotValidException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
