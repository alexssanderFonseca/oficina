package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto;

import java.math.BigDecimal;
import java.util.List;

public record CadastrarPecaInsumoDTO(
    String nome,
    String descricao,
    String codigoFabricante,
    String marca,
    List<Long> modelosCompativeisIds,
    Integer quantidadeEstoque,
    BigDecimal precoCusto,
    BigDecimal precoVenda,
    String categoria
) {}
