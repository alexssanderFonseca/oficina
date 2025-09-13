package br.com.alexsdm.postech.oficina.cliente.domain.exception;

public class ClienteNaoEncontradoException extends ClienteException {
    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado");
    }
}
