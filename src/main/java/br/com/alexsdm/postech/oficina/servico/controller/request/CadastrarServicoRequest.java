package br.com.alexsdm.postech.oficina.servico.controller.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CadastrarServicoRequest(
    UUID id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer duracaoEstimada,
    String categoria,
    Boolean ativo,
    LocalDateTime dataCadastro,
    LocalDateTime dataAtualizacao
) {}
