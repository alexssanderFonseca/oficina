package br.com.alexsdm.postech.oficina.module.orcamento.adapter.in.controller.mapper;

import br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.input.CriarOrcamentoInput;
import br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.output.BuscarOrcamentoPorIdOutput;
import br.com.alexsdm.postech.oficina.module.orcamento.adapter.in.controller.request.CriarOrcamentoRequest;
import br.com.alexsdm.postech.oficina.module.orcamento.adapter.in.controller.response.OrcamentoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrcamentoControllerMapper {

    CriarOrcamentoInput toInput(CriarOrcamentoRequest request);

    OrcamentoResponse toResponse(BuscarOrcamentoPorIdOutput orcamento);
}
