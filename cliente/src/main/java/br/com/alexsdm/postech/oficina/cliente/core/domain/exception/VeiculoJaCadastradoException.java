package br.com.alexsdm.postech.oficina.cliente.core.domain.exception;

public class VeiculoJaCadastradoException extends ClienteException {
    public VeiculoJaCadastradoException() {
        super("Veiculo ja foi cadastrado na base");
    }
}
