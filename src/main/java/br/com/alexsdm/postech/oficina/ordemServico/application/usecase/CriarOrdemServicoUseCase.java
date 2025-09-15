package br.com.alexsdm.postech.oficina.ordemServico.application.usecase;

import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.dto.CriarOrdemServicoDTO;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;

public interface CriarOrdemServicoUseCase {
    OrdemServico executar(CriarOrdemServicoDTO dto);
}
