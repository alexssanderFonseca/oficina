package br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.in.controller;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input.AtualizarPecaInsumoInput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input.CadastrarPecaInsumoInput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.output.BuscarPecaInsumoPorIdUseCaseOutput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.output.ListarPecasInsumoUseCaseOutput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.in.controller.request.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.in.controller.request.CadastrarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.in.controller.response.PecaInsumoResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PecaInsumoControllerMapper {

    CadastrarPecaInsumoInput toInput(CadastrarPecaInsumoRequest request);

    AtualizarPecaInsumoInput toInput(UUID id, AtualizarPecaInsumoRequest request);

    PecaInsumoResponse toResponse(BuscarPecaInsumoPorIdUseCaseOutput pecaInsumo);

    PecaInsumoResponse toResponse(ListarPecasInsumoUseCaseOutput listaPecaInsumosOutput);
}
