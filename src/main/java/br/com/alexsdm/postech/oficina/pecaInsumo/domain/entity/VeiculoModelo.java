package br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoModelo {
    private Long id;
    private String marca;
    private String modelo;
    private Integer anoInicio;
    private Integer anoFim;
    private String tipo;
}