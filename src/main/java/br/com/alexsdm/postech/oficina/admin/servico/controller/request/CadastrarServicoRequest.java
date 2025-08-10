package br.com.alexsdm.postech.oficina.admin.servico.controller.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CadastrarServicoRequest(
    String nome,
    String descricao,
    BigDecimal preco,
    Integer duracaoEstimada,
    String categoria
) {}
