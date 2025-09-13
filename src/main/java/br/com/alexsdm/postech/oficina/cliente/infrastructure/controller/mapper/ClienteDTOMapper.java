package br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.mapper;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdOutput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.EnderecoInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.EnderecoOutput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.VeiculoOutput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.request.EnderecoAtualizarRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ClienteDTOMapper {

    public CadastrarClienteInput toInput(CadastrarClienteRequest request) {
        var enderecoRequest = request.endereco();
        var enderecoInput = EnderecoInput.builder()
                .rua(enderecoRequest.rua())
                .numero(enderecoRequest.numero())
                .bairro(enderecoRequest.bairro())
                .cep(enderecoRequest.cep())
                .cidade(enderecoRequest.cidade())
                .uf(enderecoRequest.uf())
                .build();

        return CadastrarClienteInput.builder()
                .nome(request.nome())
                .sobrenome(request.sobrenome())
                .cpfCnpj(request.cpfCnpj())
                .email(request.email())
                .telefone(request.telefone())
                .endereco(enderecoInput)
                .build();
    }

    public BuscarClientePorIdOutput toOutput(Cliente cliente) {
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
                        .placa(veiculo.placa())
                        .marca(veiculo.veiculoModelo().marca())
                        .modelo(veiculo.veiculoModelo().modelo())
                        .cor(veiculo.cor())
                        .ano(veiculo.ano())
                        .build()) // Fixed veiculoModelo.getMarca() to veiculoModelo.marca()
                .collect(Collectors.toList());

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

    public AdicionarVeiculoInput toAdicionarVeiculoInput(UUID clienteId, AdicionarDadosVeiculoRequest request) {
        return AdicionarVeiculoInput.builder()
                .clienteId(clienteId)
                .veiculoModeloId(request.veiculoModeloId())
                .placa(request.placa())
                .ano(request.ano())
                .cor(request.cor())
                .build();
    }

    public AtualizarClienteInput toAtualizarClienteInput(UUID clienteId, AtualizarClienteRequest request) {
        return AtualizarClienteInput.builder()
                .clienteId(clienteId)
                .email(request.email())
                .telefone(request.telefone())
                .endereco(toEnderecoInput(request.endereco()))
                .build();
    }

    private EnderecoInput toEnderecoInput(EnderecoAtualizarRequest enderecoRequest) {
        if (enderecoRequest == null) {
            return null;
        }
        return EnderecoInput.builder()
                .rua(enderecoRequest.rua())
                .numero(enderecoRequest.numero())
                .bairro(enderecoRequest.bairro())
                .cep(enderecoRequest.cep())
                .cidade(enderecoRequest.cidade())
                .uf(enderecoRequest.uf())
                .build();
    }
}
