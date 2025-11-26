package br.com.alexsdm.postech.oficina.servico.core.port.in;

import br.com.alexsdm.postech.oficina.servico.core.usecase.input.CadastrarServicoInput;

import java.util.UUID;

public interface CadastrarServicoUseCase {
    UUID executar(CadastrarServicoInput dto);
}
