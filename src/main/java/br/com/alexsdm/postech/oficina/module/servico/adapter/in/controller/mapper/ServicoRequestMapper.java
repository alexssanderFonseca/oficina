package br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.mapper;


import br.com.alexsdm.postech.oficina.module.servico.core.usecase.input.AtualizarServicoInput;
import br.com.alexsdm.postech.oficina.module.servico.core.usecase.input.CadastrarServicoInput;
import br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.request.AtualizarServicoRequest;
import br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.request.CadastrarServicoRequest;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ServicoRequestMapper {

    CadastrarServicoInput toInput(CadastrarServicoRequest cadastrarServicoInput);

    AtualizarServicoInput toInput(UUID id, AtualizarServicoRequest atualizarServicoRequest);
}
