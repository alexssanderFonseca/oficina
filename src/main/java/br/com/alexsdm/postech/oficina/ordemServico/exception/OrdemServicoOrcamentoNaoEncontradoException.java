package br.com.alexsdm.postech.oficina.ordemServico.exception;

public class OrdemServicoOrcamentoNaoEncontradoException extends OrdemServicoException {

    public OrdemServicoOrcamentoNaoEncontradoException() {
        super("Orcamento relacionado a ordem de serviço não encontrado para o id solicitado");
    }
}
