package br.com.alexsdm.postech.oficina.veiculomodelo.application.gateway;

import br.com.alexsdm.postech.oficina.veiculomodelo.domain.entity.VeiculoModelo;
import java.util.Optional;

public interface VeiculoModeloGateway {
    VeiculoModelo salvar(VeiculoModelo veiculoModelo);
    Optional<VeiculoModelo> buscarPorId(Long id);
    void deletar(Long id);
}
