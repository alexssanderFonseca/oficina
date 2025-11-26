package br.com.alexsdm.postech.oficina.cliente.adapters.out.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
public class ClienteJpaEntity {

    @Id
    private UUID id;

    private String nome;

    private String sobrenome;

    @Column(unique = true, name = "cpf_cnpj")
    private String cpfCnpj;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private EnderecoJpaEntity endereco;

    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VeiculoJpaEntity> veiculos;

    public ClienteJpaEntity(UUID id, String nome, String sobrenome, String cpfCnpj, String email, EnderecoJpaEntity endereco, String telefone, List<VeiculoJpaEntity> veiculos) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
        this.veiculos = veiculos;
    }
}
