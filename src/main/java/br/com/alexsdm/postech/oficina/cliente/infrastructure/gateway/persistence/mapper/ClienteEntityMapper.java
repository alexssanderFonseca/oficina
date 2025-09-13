package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.mapper;

import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.model.ClienteEntity;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.model.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteEntityMapper {

    public ClienteEntity toEntity(Cliente cliente) {
        var endereco = cliente.getEndereco();
        var enderecoEntity = new EnderecoEntity(
                endereco.id(),
                endereco.rua(),
                endereco.numero(),
                endereco.bairro(),
                endereco.cep(),
                endereco.cidade(),
                endereco.uf()
        );

        return new ClienteEntity(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCpfCnpj(),
                cliente.getEmail(),
                enderecoEntity,
                cliente.getTelefone(),
                null // Veiculos não são mapeados na criação inicial
        );
    }

    public Cliente toDomain(ClienteEntity entity) {
        var enderecoEntity = entity.getEndereco();
        var endereco = Endereco.builder()
                .id(enderecoEntity.getId())
                .rua(enderecoEntity.getRua())
                .numero(enderecoEntity.getNumero())
                .bairro(enderecoEntity.getBairro())
                .cep(enderecoEntity.getCep())
                .cidade(enderecoEntity.getCidade())
                .uf(enderecoEntity.getUf())
                .build();

        return Cliente.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .sobrenome(entity.getSobrenome())
                .cpfCnpj(entity.getCpfCnpj())
                .email(entity.getEmail())
                .endereco(endereco)
                .telefone(entity.getTelefone())
                .build();
    }
}
