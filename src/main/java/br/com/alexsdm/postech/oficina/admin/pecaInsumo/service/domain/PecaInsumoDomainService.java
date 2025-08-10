package br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.domain;


import br.com.alexsdm.postech.oficina.admin.pecaInsumo.exception.PecaInsumoIndisponivelException;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PecaInsumoDomainService {

    public void retirarItemEstoque(PecaInsumo peca, Integer quantidade) {
        if (!peca.isDisponivel() || quantidade > peca.getQuantidadeEstoque()) {
            throw new PecaInsumoIndisponivelException();
        }
        peca.retirar(quantidade);
    }
}
