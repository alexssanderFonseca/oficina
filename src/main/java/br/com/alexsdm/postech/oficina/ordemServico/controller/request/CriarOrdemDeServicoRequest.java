package br.com.alexsdm.postech.oficina.ordemServico.controller.request;


import java.util.UUID;

public record CriarOrdemDeServicoRequest(
    String cpfCnpj,
    UUID veiculoId,
    Long orcamentoId
) {}
