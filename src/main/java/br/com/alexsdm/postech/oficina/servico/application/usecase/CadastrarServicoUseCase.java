package br.com.alexsdm.postech.oficina.servico.application.usecase;

import br.com.alexsdm.postech.oficina.servico.application.usecase.input.CadastrarServicoInput;
import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;

public interface CadastrarServicoUseCase {
    Long executar(CadastrarServicoInput dto);
}
