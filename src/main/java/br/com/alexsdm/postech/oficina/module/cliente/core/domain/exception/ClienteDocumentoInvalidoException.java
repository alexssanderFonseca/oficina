package br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception;

public class ClienteDocumentoInvalidoException extends ClienteException {

    public ClienteDocumentoInvalidoException() {
        super("CPF/CNPJ inválido");
    }
}
