package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller.response;
import java.math.BigDecimal;
public record PecaInsumoResponse(Long id, String nome, BigDecimal precoVenda, Integer quantidadeEstoque) {}
