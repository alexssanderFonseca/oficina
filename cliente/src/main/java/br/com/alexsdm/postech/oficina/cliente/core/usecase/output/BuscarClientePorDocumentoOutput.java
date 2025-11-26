package br.com.alexsdm.postech.oficina.cliente.core.usecase.output;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
public record BuscarClientePorDocumentoOutput(
        UUID id,
        String nome,
        String sobrenome,
        String cpfCnpj,
        String email,
        String telefone,
        BuscarClientePorDocumentoEnderecoOutput endereco,
        List<BuscarClientePorDocumentoVeiculoOutput> veiculos
) {

    public static BuscarClientePorDocumentoOutput toOutput(Cliente cliente) {
        var endereco = cliente.getEndereco();
        var enderecoOutput = BuscarClientePorDocumentoEnderecoOutput.builder()
                .rua(endereco.rua())
                .numero(endereco.numero())
                .bairro(endereco.bairro())
                .cep(endereco.cep())
                .cidade(endereco.cidade())
                .uf(endereco.uf())
                .build();

        var veiculosOutput = Optional.ofNullable(cliente.getVeiculos()).orElse(Collections.emptyList()).stream()
                .map(veiculo -> BuscarClientePorDocumentoVeiculoOutput.builder()
                        .id(veiculo.getId().toString())
                        .placa(veiculo.getPlaca())
                        .marca(veiculo.getMarca())
                        .modelo(veiculo.getModelo())
                        .cor(veiculo.getCor())
                        .ano(veiculo.getAno())
                        .build())
                .toList();

        return BuscarClientePorDocumentoOutput.builder()
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
