package br.com.alexsdm.postech.oficina.admin.cliente.service.mapper;

import br.com.alexsdm.postech.oficina.admin.cliente.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.admin.cliente.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.admin.cliente.service.input.AdicionarVeiculoClientInput;
import br.com.alexsdm.postech.oficina.admin.cliente.service.input.CadastrarClienteInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteServiceMapper {

    CadastrarClienteInput toCadastrarClienteInput(CadastrarClienteRequest request);

    AdicionarVeiculoClientInput toAdicionarVeiculoClientInput(AdicionarDadosVeiculoRequest adicionarDadosVeiculoRequest);

}
