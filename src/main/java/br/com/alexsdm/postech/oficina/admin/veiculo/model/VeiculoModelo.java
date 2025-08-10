package br.com.alexsdm.postech.oficina.admin.veiculo.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "veiculo_modelo")
@Getter
public class VeiculoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;

    private String modelo;

    private Integer anoInicio;

    private Integer anoFim;

    private String tipo;

    public VeiculoModelo() {
    }

    public VeiculoModelo(String marca, String modelo, Integer anoInicio, Integer anoFim, String tipo) {
        this.marca = marca;
        this.modelo = modelo;
        this.anoInicio = anoInicio;
        this.anoFim = anoFim;
        this.tipo = tipo;
    }

}
