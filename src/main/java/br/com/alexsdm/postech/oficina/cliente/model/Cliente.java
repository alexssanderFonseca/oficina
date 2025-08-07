package br.com.alexsdm.postech.oficina.cliente.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Entity
public class Cliente {

    @Id
    private UUID id;

    private String nome;

    private String sobrenome;

    @Column(unique = true, name = "cpf_cnpj")
    private String cpfCnpj;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    private String telefone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id")
    private List<Veiculo> veiculos;

    public Cliente() {
    }

    public Cliente(UUID id,
                   String nome,
                   String sobrenome,
                   String cpfCnpj,
                   String email,
                   Endereco endereco,
                   String telefone) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
        this.veiculos = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public Optional<Veiculo> getVeiculoPorId(UUID id) {
        return this.veiculos.stream()
                .filter(veiculo -> veiculo.getId().equals(id))
                .findFirst();

    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }
}
