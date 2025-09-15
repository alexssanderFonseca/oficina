package br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.mapper;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdOutput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.CadastrarClienteRequest;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ClienteDTOMapper {
    CadastrarClienteInput toInput(CadastrarClienteRequest request);

    BuscarClientePorIdOutput toOutput(Cliente cliente);

    AdicionarVeiculoInput toAdicionarVeiculoInput(UUID clienteId, AdicionarDadosVeiculoRequest request);

    AtualizarClienteInput toAtualizarClienteInput(UUID clienteId, AtualizarClienteRequest request);
}
