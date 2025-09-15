package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteVeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.AdicionarVeiculoUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AdicionarVeiculoOutput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.exception.VeiculoModeloNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Named
@RequiredArgsConstructor
@Slf4j
public class AdicionarVeiculoUseCaseImpl implements AdicionarVeiculoUseCase {

    private final ClienteGateway clienteGateway;
    private final ClienteVeiculoModeloGateway clienteVeiculoModeloGateway;

    @Override
    public AdicionarVeiculoOutput executar(AdicionarVeiculoInput input) {
        log.info("Iniciando adição de veículo para o cliente {}", input.clienteId());
        var cliente = clienteGateway.buscarPorId(input.clienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + input.clienteId()));

        log.info("Buscando modelo de veículo com ID {}", input.veiculoModeloId());
        var veiculoModelo = clienteVeiculoModeloGateway.buscarPorId(input.veiculoModeloId())
                .orElseThrow(() -> {
                    log.error("Modelo de veículo não encontrado com o ID: {}", input.veiculoModeloId());
                    return new VeiculoModeloNaoEncontradoException("Modelo de veículo não encontrado com o ID: " + input.veiculoModeloId());
                });

        var novoVeiculo = new Veiculo(
                UUID.randomUUID(),
                input.placa(),
                veiculoModelo,
                input.cor(),
                input.ano()
        );

        cliente.adicionarVeiculo(novoVeiculo);
        clienteGateway.salvar(cliente);
        log.info("Veículo {} adicionado com sucesso ao cliente {}", novoVeiculo.getId(), cliente.getId());

        return new AdicionarVeiculoOutput(novoVeiculo.getId());
    }
}
