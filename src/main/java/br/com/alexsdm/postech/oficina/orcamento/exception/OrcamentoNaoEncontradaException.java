package br.com.alexsdm.postech.oficina.orcamento.exception;

public class OrcamentoNaoEncontradaException extends OrcamentoException {

    public OrcamentoNaoEncontradaException() {
        super("Orcamento não encontrado para o id solicitado");
    }
}
