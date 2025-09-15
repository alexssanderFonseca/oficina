package br.com.alexsdm.postech.oficina.ordemServico.domain.exception;

public class OrdemServicoNaoEncontradaException extends OrdemServicoException {

    public OrdemServicoNaoEncontradaException() {
        super("Ordem de servico não encontrada para o id solicitado");
    }
}
