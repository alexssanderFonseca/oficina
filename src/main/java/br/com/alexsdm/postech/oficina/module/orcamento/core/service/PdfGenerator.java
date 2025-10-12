package br.com.alexsdm.postech.oficina.module.orcamento.core.service;

import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Orcamento;

public interface PdfGenerator {
    byte[] generate(Orcamento orcamento,
                    Cliente cliente);
}
