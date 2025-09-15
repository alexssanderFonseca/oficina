package br.com.alexsdm.postech.oficina.orcamento.application.usecase;

import br.com.alexsdm.postech.oficina.orcamento.application.usecase.dto.CriarOrcamentoDTO;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Orcamento;

public interface CriarOrcamentoUseCase {
    Orcamento executar(CriarOrcamentoDTO dto);
}
