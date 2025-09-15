package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.impl;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.FinalizarDiagnosticoUseCase;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.dto.FinalizarDiagnosticoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class FinalizarDiagnosticoUseCaseImpl implements FinalizarDiagnosticoUseCase {
    @Override public Long executar(FinalizarDiagnosticoDTO dto) { /* TODO: Lógica de negócio */ return 1L; }
}