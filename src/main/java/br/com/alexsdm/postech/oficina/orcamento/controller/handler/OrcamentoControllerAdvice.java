package br.com.alexsdm.postech.oficina.orcamento.controller.handler;


import br.com.alexsdm.postech.oficina.commons.ApiError;
import br.com.alexsdm.postech.oficina.orcamento.exception.OrcamentoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrcamentoControllerAdvice {

    @ExceptionHandler(OrcamentoException.class)
    public ResponseEntity<ApiError> handleMyCustomException(OrcamentoException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
