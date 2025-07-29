package br.com.alexsdm.postech.oficina.veiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "veiculo_modelo")
public class VeiculoModelo {

    @Id
    private UUID id;

    private String marca;

    private String modelo;

    private Integer anoInicio;  // Ano inicial do modelo (ex: 2015)

    private Integer anoFim;     // Ano final do modelo (ex: 2020), pode ser null

    private String tipo;        // Exemplo: "SUV", "Sedan", "Hatch", etc

    public VeiculoModelo() {}

    public VeiculoModelo(UUID id, String marca, String modelo, Integer anoInicio, Integer anoFim, String tipo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anoInicio = anoInicio;
        this.anoFim = anoFim;
        this.tipo = tipo;
    }

    public UUID getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public Integer getAnoFim() {
        return anoFim;
    }

    public String getTipo() {
        return tipo;
    }
}
