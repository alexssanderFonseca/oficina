package br.com.alexsdm.postech.oficina.veiculo.exception;

public class VeiculoModeloNaoEncontradoException extends RuntimeException {
    public VeiculoModeloNaoEncontradoException() {
        super("Modelo de veículo não encontrado.");
    }
}
