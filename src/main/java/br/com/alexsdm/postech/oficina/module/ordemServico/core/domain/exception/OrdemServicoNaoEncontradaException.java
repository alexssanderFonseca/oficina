package br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.exception;

public class OrdemServicoNaoEncontradaException extends OrdemServicoException {

    public OrdemServicoNaoEncontradaException() {
        super("Ordem de servico não encontrada para o id solicitado");
    }
}