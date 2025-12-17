package br.com.alexsdm.postech.oficina.orcamento.adapter.out.gateway;

import br.com.alexsdm.postech.oficina.cliente.core.port.in.BuscarClientePorDocumentoUsecase;
import br.com.alexsdm.postech.oficina.cliente.core.port.in.BuscarClientePorIdUseCase;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.ClienteOrcamentoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.orcamento.core.exception.OrcamentoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClienteOrcamentoGatewayAdapter implements ClienteOrcamentoPort {

    private final BuscarClientePorIdUseCase buscarClientePorIdUseCase;
    private final BuscarClientePorDocumentoUsecase buscarClientePorDocumentoUsecase;

    @Override
    public Optional<Cliente> buscarClienteComVeiculo(UUID idCliente,
                                                     UUID idVeiculo) {

        var buscarClienteOutput = buscarClientePorIdUseCase.executar(idCliente);
        var veiculo = buscarClienteOutput.veiculos()
                .stream().
                filter(veiculoOutput -> idVeiculo.toString().equals(veiculoOutput.id()))
                .findFirst()
                .orElseThrow(() -> new OrcamentoException("Veiculo invalido"));

        return Optional.of(new Cliente(
                buscarClienteOutput.id(),
                buscarClienteOutput.nome(),
                buscarClienteOutput.sobrenome(),
                buscarClienteOutput.cpfCnpj(),
                veiculo.id(),
                veiculo.placa(),
                veiculo.marca(),
                veiculo.ano(),
                veiculo.modelo()
        ));

    }

    @Override
    public Optional<Cliente> buscarClienteComVeiculo(String cpfCnpj, UUID idVeiculo) {
        var buscarClientePorDocumentoOutput = buscarClientePorDocumentoUsecase.executar(cpfCnpj);

        var veiculo = buscarClientePorDocumentoOutput.veiculos()
                .stream().
                filter(veiculoOutput -> idVeiculo.toString().equals(veiculoOutput.id()))
                .findFirst()
                .orElseThrow(() -> new OrcamentoException("Veiculo invalido"));


        return Optional.of(new Cliente(
                buscarClientePorDocumentoOutput.id(),
                buscarClientePorDocumentoOutput.nome(),
                buscarClientePorDocumentoOutput.sobrenome(),
                buscarClientePorDocumentoOutput.cpfCnpj(),
                veiculo.id(),
                veiculo.placa(),
                veiculo.marca(),
                veiculo.ano(),
                veiculo.modelo()
        ));


    }


}
