package br.com.alexsdm.postech.oficina.ordemServico.service.domain;

import br.com.alexsdm.postech.oficina.ordemServico.entity.ItemPecaOrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.servico.entity.Servico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemServicoDomainService {


    public void iniciarDiagnostico(OrdemServico ordemServico) {
        ordemServico.diagnosticar();
    }

    public void finalizarDiagnostico(OrdemServico ordemServico) {
        ordemServico.finalizarDiagnostico();
    }

    public void executar(OrdemServico ordemServico,
                         List<ItemPecaOrdemServico> itemPecaOS,
                         List<Servico> servicos) {
        ordemServico.executar(itemPecaOS, servicos);

    }


    public void finalizar(OrdemServico ordemServico) {
        ordemServico.finalizar();
    }

    public void entregar(OrdemServico ordemServico) {
        ordemServico.entregar();
    }


}
