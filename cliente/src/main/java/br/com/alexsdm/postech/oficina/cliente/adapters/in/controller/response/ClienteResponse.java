package br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.response;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;

import java.util.UUID;

public record ClienteResponse(
        UUID id,
        String nome,
        String sobrenome,
        String cpfCnpj,
        String email,
        String telefone
) {
    public static ClienteResponse fromDomain(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCpfCnpj(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }
}
