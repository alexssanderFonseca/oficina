package br.com.alexsdm.postech.oficina.peca.service.domain;


import br.com.alexsdm.postech.oficina.peca.model.Peca;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PecaDomainService {

    public void retirarItemEstoque(Peca peca, Integer quantidade) {
        if (quantidade > peca.getQuantidadeEstoque()) {
            throw new RuntimeException();
        }
        peca.retirar(quantidade);
    }
}
