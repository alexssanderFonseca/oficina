package br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {
    private UUID id;
    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String ano;

    public String getDescricaoCompleta() {
        return this.getMarca() + " - " + this.getModelo() + " - " + this.getAno() + " - " + this.getCor();
    }
}
