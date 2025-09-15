package br.com.alexsdm.postech.oficina.ordemServico.application.usecase.dto;
import java.util.List;
public record FinalizarDiagnosticoDTO(Long osId, List<ItemPecaDTO> pecas, List<Long> servicosIds) {
    public record ItemPecaDTO(Long pecaId, Integer quantidade) {}
}