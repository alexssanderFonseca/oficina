package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.port.in.AdicionarVeiculoUseCase;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.AdicionarVeiculoOutput;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Veiculo;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Named
@RequiredArgsConstructor
@Slf4j
public class AdicionarVeiculoUseCaseImpl implements AdicionarVeiculoUseCase {

    private final ClienteRepository clienteRepository;

    @Override
    public AdicionarVeiculoOutput executar(AdicionarVeiculoInput input) {
        log.info("Iniciando adição de veículo para o cliente {}", input.clienteId());
        var cliente = clienteRepository.buscarPorId(input.clienteId())
                .orElseThrow(ClienteNaoEncontradoException::new);

        var novoVeiculo = new Veiculo(
                UUID.randomUUID(),
                input.placa(),
                input.marca(),
                input.modelo(),
                input.cor(),
                input.ano()
        );

        cliente.adicionarVeiculo(novoVeiculo);
        clienteRepository.salvar(cliente);
        log.info("Veículo {} adicionado com sucesso ao cliente {}", novoVeiculo.getId(), cliente.getId());

        return new AdicionarVeiculoOutput(novoVeiculo.getId());
    }
}
