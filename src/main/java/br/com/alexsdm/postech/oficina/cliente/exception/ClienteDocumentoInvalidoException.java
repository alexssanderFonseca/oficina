package br.com.alexsdm.postech.oficina.cliente.exception;

public class ClienteDocumentoInvalidoException extends ClienteException {

    public ClienteDocumentoInvalidoException() {
        super("CPF/CNPJ inválido");
    }
}
