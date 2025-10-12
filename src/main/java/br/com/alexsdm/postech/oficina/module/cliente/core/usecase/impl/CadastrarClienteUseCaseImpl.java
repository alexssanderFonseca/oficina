package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.port.in.CadastrarClienteUseCase;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.CadastrarClienteOutput;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class CadastrarClienteUseCaseImpl implements CadastrarClienteUseCase {

    private final ClienteRepository clienteRepository;

    @Override
    public CadastrarClienteOutput executar(CadastrarClienteInput input) {
        clienteRepository.buscarPorDocumento(input.cpfCnpj())
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

        var clienteSalvo = clienteRepository.salvar(cliente);
        return new CadastrarClienteOutput(clienteSalvo.getId());
    }
}
