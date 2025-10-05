package br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.input.FinalizarDiagnosticoInput;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.output.FinalizarDiagnosticoOutput;

public interface FinalizarDiagnosticoUseCase {
    FinalizarDiagnosticoOutput executar(FinalizarDiagnosticoInput dto);
}