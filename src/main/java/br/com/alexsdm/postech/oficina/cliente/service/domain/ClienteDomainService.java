package br.com.alexsdm.postech.oficina.cliente.service.domain;

import br.com.alexsdm.postech.oficina.cliente.model.Cliente;
import br.com.alexsdm.postech.oficina.cliente.model.Veiculo;
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
