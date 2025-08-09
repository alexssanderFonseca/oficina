package br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.domain;


import br.com.alexsdm.postech.oficina.admin.pecaInsumo.exception.QuantidadeIndisponivelException;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PecaInsumoDomainService {

    public void retirarItemEstoque(PecaInsumo peca, Integer quantidade) {
        if (quantidade > peca.getQuantidadeEstoque()) {
            throw new QuantidadeIndisponivelException();
        }
        peca.retirar(quantidade);
    }
}
