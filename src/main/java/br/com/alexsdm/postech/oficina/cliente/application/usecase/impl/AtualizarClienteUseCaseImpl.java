package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.AtualizarClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdOutput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.mapper.ClienteDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarClienteUseCaseImpl implements AtualizarClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final ClienteDTOMapper clienteDTOMapper;

    @Override
    public BuscarClientePorIdOutput executar(AtualizarClienteInput input) {
        var cliente = clienteGateway.buscarPorId(input.clienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + input.clienteId()));

        cliente.atualizarEmail(input.email());
        cliente.atualizarTelefone(input.telefone());

        if (input.endereco() != null) {
            var novoEndereco = Endereco.builder()
                    .rua(input.endereco().rua())
                    .numero(input.endereco().numero())
                    .bairro(input.endereco().bairro())
                    .cep(input.endereco().cep())
                    .cidade(input.endereco().cidade())
                    .uf(input.endereco().uf())
                    .build();
            cliente.atualizarEndereco(novoEndereco);
        }

        var clienteAtualizado = clienteGateway.salvar(cliente);
        return clienteDTOMapper.toOutput(clienteAtualizado);
    }
}
