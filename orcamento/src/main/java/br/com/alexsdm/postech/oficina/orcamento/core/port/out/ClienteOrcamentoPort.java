package br.com.alexsdm.postech.oficina.orcamento.core.port.out;

import br.com.alexsdm.postech.oficina.orcamento.core.entity.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteOrcamentoPort {

    Optional<Cliente> buscarClienteComVeiculo(UUID idCliente,
                                              UUID idVeiculo);


    Optional<Cliente> buscarClienteComVeiculo(String cpfCnpj,
                                              UUID idVeiculo);
}
