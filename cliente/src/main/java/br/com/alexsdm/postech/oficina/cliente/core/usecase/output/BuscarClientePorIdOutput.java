package br.com.alexsdm.postech.oficina.cliente.core.usecase.output;

import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.EnderecoOutput;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.VeiculoOutput;
import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record BuscarClientePorIdOutput(UUID id,
                                       String nome,
                                       String sobrenome,
                                       String cpfCnpj,
                                       String email,
                                       String telefone,
                                       EnderecoOutput endereco,
                                       List<VeiculoOutput> veiculos) {

    public static BuscarClientePorIdOutput toOutput(Cliente cliente) {
        var endereco = cliente.getEndereco();
        var enderecoOutput = EnderecoOutput.builder()
                .rua(endereco.rua())
                .numero(endereco.numero())
                .bairro(endereco.bairro())
                .cep(endereco.cep())
                .cidade(endereco.cidade())
                .uf(endereco.uf())
                .build();

        var veiculosOutput = Optional.ofNullable(cliente.getVeiculos()).orElse(Collections.emptyList()).stream()
                .map(veiculo -> VeiculoOutput.builder()
                        .id(veiculo.getId().toString())
                        .placa(veiculo.getPlaca())
                        .marca(veiculo.getMarca())
                        .modelo(veiculo.getModelo())
                        .cor(veiculo.getCor())
                        .ano(veiculo.getAno())
                        .build())
                .toList();
        return BuscarClientePorIdOutput.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .sobrenome(cliente.getSobrenome())
                .cpfCnpj(cliente.getCpfCnpj())
                .email(cliente.getEmail())
                .telefone(cliente.getTelefone())
                .endereco(enderecoOutput)
                .veiculos(veiculosOutput)
                .build();
    }
}


