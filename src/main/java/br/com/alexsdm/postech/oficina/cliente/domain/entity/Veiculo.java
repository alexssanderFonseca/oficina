package br.com.alexsdm.postech.oficina.cliente.domain.entity;

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
    private VeiculoModelo veiculoModelo;
    private String cor;
    private String ano;

    public String getDescricaoCompleta() {
        return veiculoModelo.getMarca() + " - " + veiculoModelo.getModelo() + " - " + this.getAno() + " - " + this.getCor();
    }
}
