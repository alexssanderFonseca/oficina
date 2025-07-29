package br.com.alexsdm.postech.oficina.clientes.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Entity
public class Cliente {

    @Id
    private UUID id;

    private String nome;

    private String sobrenome;

    private String cpf;

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
                   String cpf,
                   String email,
                   Endereco endereco,
                   String telefone ){
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
        this.veiculos = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
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

    public String getCpf() {
        return cpf;
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
}
