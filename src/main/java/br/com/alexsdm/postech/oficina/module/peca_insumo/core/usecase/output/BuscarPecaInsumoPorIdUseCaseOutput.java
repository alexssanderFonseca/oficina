package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BuscarPecaInsumoPorIdUseCaseOutput(
        UUID id,
        String nome,
        String descricao,
        String codigoFabricante,
        String marca,
        Integer quantidadeEstoque,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        String categoria,
        Boolean ativo,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao
) {
}
