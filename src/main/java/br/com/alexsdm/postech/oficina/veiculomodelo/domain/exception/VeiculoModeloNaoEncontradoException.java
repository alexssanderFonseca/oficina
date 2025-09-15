package br.com.alexsdm.postech.oficina.veiculomodelo.domain.exception;

public class VeiculoModeloNaoEncontradoException extends RuntimeException {
    public VeiculoModeloNaoEncontradoException() {
        super("Modelo de veículo não encontrado.");
    }
}
