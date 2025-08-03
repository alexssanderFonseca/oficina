package br.com.alexsdm.postech.oficina.cliente.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity

public class Endereco {

    @Id
    private UUID id;

    private String rua;

    private String bairro;

    private String cep;

    private String cidade;

    private String uf;

    public Endereco() {}

    public Endereco(UUID id,
                    String rua,
                    String bairro,
                    String cep,
                    String cidade,
                    String uf) {
        this.id = id;
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.uf = uf;
    }

    public UUID getId() {
        return id;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }
}
