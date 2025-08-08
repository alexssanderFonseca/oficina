package br.com.alexsdm.postech.oficina.orcamento.service.domain;


import br.com.alexsdm.postech.oficina.orcamento.model.Orcamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrcamentoDomainService {

    //TODO gerar pdf
    public void enviar() {
    }

    public void aprovar(Orcamento orcamento) {
        orcamento.aceitar();

    }

    public void recusar(Orcamento orcamento) {
        orcamento.recusar();

    }


}



