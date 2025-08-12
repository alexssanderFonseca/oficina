package br.com.alexsdm.postech.oficina.cliente.entity;

import br.com.alexsdm.postech.oficina.cliente.entity.validation.ValidadorPlacaVeiculo;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteException;
import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.util.UUID;


@Entity
@Getter
public class Veiculo {

    @Id
    private UUID id;
    private String placa;
    @ManyToOne
    @JoinColumn(name = "veiculo_modelo_id", nullable = false)
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

        if (!ValidadorPlacaVeiculo.isValida(placa)) {
            throw new ClienteException("Veiculo informado do cliente possui uma placa invalida");
        }

        this.id = id;
        this.placa = placa;
        this.ano = ano;
        this.cor = cor;
        this.veiculoModelo = veiculoModelo;
    }

    public String getDescricaoCompleta() {
        return veiculoModelo.getMarca() + " - " + veiculoModelo.getModelo() + " - " +this.getAno() + " - " + this.getCor();
    }

}
