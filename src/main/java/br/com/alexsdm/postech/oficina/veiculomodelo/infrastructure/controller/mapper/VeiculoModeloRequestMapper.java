package br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.controller.mapper;

import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.dto.CadastrarVeiculoModeloInput;
import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.controller.request.CadastrarVeiculoModeloRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoModeloRequestMapper {

    CadastrarVeiculoModeloInput toInput(CadastrarVeiculoModeloRequest request);
}
