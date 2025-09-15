package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto.AtualizarPecaInsumoDTO;
import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto.CadastrarPecaInsumoDTO;
import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller.request.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller.request.CadastrarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller.response.PecaInsumoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PecaInsumoControllerMapper {

    CadastrarPecaInsumoDTO toDTO(CadastrarPecaInsumoRequest request);

    AtualizarPecaInsumoDTO toDTO(Long id, AtualizarPecaInsumoRequest request);

    PecaInsumoResponse toResponse(PecaInsumo pecaInsumo);
}
