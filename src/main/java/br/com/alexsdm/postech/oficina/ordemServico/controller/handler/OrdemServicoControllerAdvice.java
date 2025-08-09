package br.com.alexsdm.postech.oficina.ordemServico.controller.handler;


import br.com.alexsdm.postech.oficina.commons.ApiError;
import br.com.alexsdm.postech.oficina.ordemServico.exception.OrdemServicoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrdemServicoControllerAdvice {

    @ExceptionHandler(OrdemServicoException.class)
    public ResponseEntity<ApiError> handleMyCustomException(OrdemServicoException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
