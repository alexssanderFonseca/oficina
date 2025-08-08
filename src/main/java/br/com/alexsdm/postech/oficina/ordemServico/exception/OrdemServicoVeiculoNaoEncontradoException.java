package br.com.alexsdm.postech.oficina.ordemServico.exception;

public class OrdemServicoVeiculoNaoEncontradoException extends OrdemServicoException {

    public OrdemServicoVeiculoNaoEncontradoException() {
        super("Veiculo relacionado a ordem de serviço não encontrado para o id solicitado");
    }
}
