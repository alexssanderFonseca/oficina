package br.com.alexsdm.postech.oficina.ordemServico.application.usecase;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
public interface BuscarOrdemServicoPorIdUseCase { OrdemServico executar(Long id); }