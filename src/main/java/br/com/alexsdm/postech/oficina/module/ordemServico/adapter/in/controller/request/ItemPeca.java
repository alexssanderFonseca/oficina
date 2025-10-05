package br.com.alexsdm.postech.oficina.module.ordemServico.adapter.in.controller.request;

import java.util.UUID;

public record ItemPeca(UUID idPeca, int qtd) {}