package br.com.alexsdm.postech.oficina.clientes.model;

import br.com.alexsdm.postech.oficina.veiculos.model.VeiculoModelo;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
public class Veiculo {

    @Id
    private UUID id;
    private String placa;
    @ManyToOne
    @JoinColumn(name = "veiculoModelo_id", nullable = false)
    private VeiculoModelo veiculoModelo;
    private String cor;
    private String ano;


    public Veiculo() {
    }

    public Veiculo(UUID id,
                   String placa,
                   String ano,
                   String cor,
                   VeiculoModelo veiculoModelo) {
        this.id = id;
        this.placa = placa;
        this.ano = ano;
        this.cor = cor;
        this.veiculoModelo = veiculoModelo;
    }

    public String getPlaca() {
        return placa;
    }


    public String getCor() {
        return cor;
    }

    public String getAno() {
        return ano;
    }
}
