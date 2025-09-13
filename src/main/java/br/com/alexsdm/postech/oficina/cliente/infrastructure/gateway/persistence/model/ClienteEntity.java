package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    private UUID id;

    private String nome;

    private String sobrenome;

    @Column(unique = true, name = "cpf_cnpj")
    private String cpfCnpj;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private EnderecoEntity endereco;

    private String telefone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id")
    private List<VeiculoEntity> veiculos;
}
