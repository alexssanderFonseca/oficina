package br.com.alexsdm.postech.oficina.servico.infrastructure.controller.mapper;


import br.com.alexsdm.postech.oficina.servico.application.usecase.input.AtualizarServicoInput;
import br.com.alexsdm.postech.oficina.servico.application.usecase.input.CadastrarServicoInput;
import br.com.alexsdm.postech.oficina.servico.infrastructure.controller.request.AtualizarServicoRequest;
import br.com.alexsdm.postech.oficina.servico.infrastructure.controller.request.CadastrarServicoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServicoRequestMapper {

    CadastrarServicoInput toInput(CadastrarServicoRequest cadastrarServicoInput);

    AtualizarServicoInput toInput(Long id, AtualizarServicoRequest cadastrarServicoInput);
}
