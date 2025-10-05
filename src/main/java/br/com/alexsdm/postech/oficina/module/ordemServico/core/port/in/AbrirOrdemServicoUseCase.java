package br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.input.CriarOrdemServicoInput;

import java.util.UUID;

public interface AbrirOrdemServicoUseCase {
    UUID executar(CriarOrdemServicoInput input);
}
