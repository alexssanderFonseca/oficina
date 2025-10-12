package br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.handler;


import br.com.alexsdm.postech.oficina.module.servico.core.domain.exception.ServicoException;
import br.com.alexsdm.postech.oficina.module.servico.core.domain.exception.ServicoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServicoControllerAdvice {

    @ExceptionHandler(ServicoException.class)
    public ResponseEntity<ApiError> handleMyCustomException(ServicoException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServicoNaoEncontradoException.class)
    public ResponseEntity<ApiError> handleMyCustomException(ServicoNaoEncontradoException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMyCustomException(MethodArgumentNotValidException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
