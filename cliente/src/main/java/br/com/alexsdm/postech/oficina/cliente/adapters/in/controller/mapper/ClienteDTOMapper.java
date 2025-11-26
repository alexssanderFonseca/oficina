package br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.mapper;

import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.BuscarClientePorIdOutput;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.request.CadastrarClienteRequest;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ClienteDTOMapper {
    CadastrarClienteInput toInput(CadastrarClienteRequest request);

    BuscarClientePorIdOutput toOutput(Cliente cliente);

    AdicionarVeiculoInput toAdicionarVeiculoInput(UUID clienteId, AdicionarDadosVeiculoRequest request);

    AtualizarClienteInput toAtualizarClienteInput(UUID clienteId, AtualizarClienteRequest request);
}
