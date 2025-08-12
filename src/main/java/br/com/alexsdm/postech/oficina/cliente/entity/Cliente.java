package br.com.alexsdm.postech.oficina.cliente.entity;

import br.com.alexsdm.postech.oficina.cliente.entity.validation.ValidadorCpfCnpj;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteDocumentoInvalidoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Entity
@Getter
@Setter
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

        if (!ValidadorCpfCnpj.isValido(cpfCnpj)) {
            throw new ClienteDocumentoInvalidoException();
        }

        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
        this.veiculos = new ArrayList<>();
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        if (this.veiculos == null) {
            this.veiculos = new ArrayList<>();
        }
        veiculos.add(veiculo);
    }

    public Optional<Veiculo> getVeiculoPorId(UUID id) {
        if (this.veiculos == null) {
            return Optional.empty();
        }
        return this.veiculos.stream()
                .filter(veiculo -> veiculo.getId().equals(id))
                .findFirst();

    }

    public void atualizarEmail(String email) {
        if (email == null || email.isBlank()) {
            return;
        }
        this.email = email;
    }

    public void atualizarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            return;
        }
        this.telefone = telefone;
    }

    public void atualizarEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
