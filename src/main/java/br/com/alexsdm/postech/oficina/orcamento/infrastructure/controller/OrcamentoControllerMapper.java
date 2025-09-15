package br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller;

import br.com.alexsdm.postech.oficina.orcamento.application.usecase.dto.CriarOrcamentoDTO;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller.request.CriacaoOrcamentoRequest;
import br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller.response.OrcamentoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrcamentoControllerMapper {

    @Mapping(target = "pecas", source = "pecas")
    @Mapping(target = "servicosIds", source = "servicos")
    CriarOrcamentoDTO toDTO(CriacaoOrcamentoRequest request);

    OrcamentoResponse toResponse(Orcamento orcamento);
}
