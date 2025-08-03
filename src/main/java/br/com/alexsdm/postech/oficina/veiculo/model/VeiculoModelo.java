package br.com.alexsdm.postech.oficina.veiculo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "veiculo_modelo")
public class VeiculoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;

    private String modelo;

    private Integer anoInicio;  // Ano inicial do modelo (ex: 2015)

    private Integer anoFim;     // Ano final do modelo (ex: 2020), pode ser null

    private String tipo;        // Exemplo: "SUV", "Sedan", "Hatch", etc

    public VeiculoModelo() {}

    public VeiculoModelo( String marca, String modelo, Integer anoInicio, Integer anoFim, String tipo) {

        this.marca = marca;
        this.modelo = modelo;
        this.anoInicio = anoInicio;
        this.anoFim = anoFim;
        this.tipo = tipo;
    }

    public Long getId() {
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
