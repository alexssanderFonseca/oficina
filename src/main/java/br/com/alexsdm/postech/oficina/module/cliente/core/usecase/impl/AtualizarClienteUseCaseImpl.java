package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.in.AtualizarClienteUseCase;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.BuscarClientePorIdOutput;
import br.com.alexsdm.postech.oficina.module.cliente.adapters.in.controller.mapper.ClienteDTOMapper;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class AtualizarClienteUseCaseImpl implements AtualizarClienteUseCase {

    private final ClienteRepository clienteRepository;
    private final ClienteDTOMapper clienteDTOMapper;

    @Override
    public BuscarClientePorIdOutput executar(AtualizarClienteInput input) {
        var cliente = clienteRepository.buscarPorId(input.clienteId())
                .orElseThrow(ClienteNaoEncontradoException::new);

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

        var clienteAtualizado = clienteRepository.salvar(cliente);
        return clienteDTOMapper.toOutput(clienteAtualizado);
    }
}
