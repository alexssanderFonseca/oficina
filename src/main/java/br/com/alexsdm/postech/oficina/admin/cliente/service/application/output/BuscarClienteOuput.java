package br.com.alexsdm.postech.oficina.admin.cliente.service.application.output;

import java.util.List;

public record BuscarClienteOuput(
        String id,
        String nome,
        String sobrenome,
        String cpfCnpj,
        String email,
        String telefone,
        BuscarClienteEnderecoOutput endereco,
        List<BuscarClienteVeiculoOutput> veiculos

) {
}
