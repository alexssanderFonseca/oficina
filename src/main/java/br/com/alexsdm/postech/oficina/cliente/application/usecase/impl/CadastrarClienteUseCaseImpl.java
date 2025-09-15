package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.CadastrarClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.CadastrarClienteOutput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteException;
import jakarta.inject.Named;
import jdk.jfr.Name;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class CadastrarClienteUseCaseImpl implements CadastrarClienteUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public CadastrarClienteOutput executar(CadastrarClienteInput input) {
        clienteGateway.buscarPorDocumento(input.cpfCnpj())
                .ifPresent(cliente -> {
            throw new ClienteException("Cliente já cadastrado!");
        });

        var endereco = Endereco.builder()
                .id(UUID.randomUUID())
                .rua(input.endereco().rua())
                .numero(input.endereco().numero())
                .bairro(input.endereco().bairro())
                .cep(input.endereco().cep())
                .cidade(input.endereco().cidade())
                .uf(input.endereco().uf())
                .build();

        var cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nome(input.nome())
                .sobrenome(input.sobrenome())
                .cpfCnpj(input.cpfCnpj())
                .email(input.email())
                .telefone(input.telefone())
                .endereco(endereco)
                .build();

        var clienteSalvo = clienteGateway.salvar(cliente);
        return new CadastrarClienteOutput(clienteSalvo.getId());
    }
}
