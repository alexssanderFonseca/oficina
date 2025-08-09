package br.com.alexsdm.postech.oficina.admin.cliente.controller.handler;


import br.com.alexsdm.postech.oficina.admin.cliente.exception.ClienteException;
import br.com.alexsdm.postech.oficina.commons.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClienteControllerAdvice {

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<ApiError> handleMyCustomException(ClienteException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
