package br.com.alexsdm.postech.oficina.admin.cliente.service.domain;

import br.com.alexsdm.postech.oficina.admin.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Veiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteDomainService {

    public UUID adicionarVeiculo(Cliente cliente, Veiculo veiculo) {
        cliente.adicionarVeiculo(veiculo);
        return veiculo.getId();
    }


}
