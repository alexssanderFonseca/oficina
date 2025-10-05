package br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.exception;

public class OrdemServicoClienteNaoEncontradoException extends OrdemServicoException {

    public OrdemServicoClienteNaoEncontradoException() {
        super("Cliente relacionado a ordem de serviço não encontrado para o id solicitado");
    }
}