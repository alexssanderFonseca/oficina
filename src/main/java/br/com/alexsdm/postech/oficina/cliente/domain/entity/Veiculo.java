package br.com.alexsdm.postech.oficina.cliente.domain.entity;

import br.com.alexsdm.postech.oficina.cliente.domain.validation.ValidadorPlacaVeiculo;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteException;
import lombok.Builder;

import java.util.UUID;

@Builder
public record Veiculo(UUID id,
                      String placa,
                      VeiculoModelo veiculoModelo,
                      String cor,
                      String ano) {

    public Veiculo {
        if (!ValidadorPlacaVeiculo.isValida(placa)) {
            throw new ClienteException("Veiculo informado possui uma placa invalida");
        }
    }

    public String getDescricaoCompleta() {
        return veiculoModelo.marca() + " - " + veiculoModelo.modelo() + " - " + this.ano() + " - " + this.cor();
    }
}