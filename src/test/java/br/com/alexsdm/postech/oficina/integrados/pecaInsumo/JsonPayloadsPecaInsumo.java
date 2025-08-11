package br.com.alexsdm.postech.oficina.integrados.pecaInsumo;

import java.util.concurrent.atomic.AtomicLong;

public class JsonPayloadsPecaInsumo {

    private static final AtomicLong counter = new AtomicLong(1);

    private static String getUniqueId() {
        return String.valueOf(counter.getAndIncrement());
    }

    public static String login() {
        return """
                {
                    "username": "admin",
                    "password": "admin"
                }
                """;
    }

    public static String pecaValida() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Filtro de Óleo %s",
                    "descricao": "Filtro de óleo original para motor",
                    "codigoFabricante": "FO-%s",
                    "marca": "Bosch",
                    "idsModelosCompativeis": [1],
                    "quantidadeEstoque": 10,
                    "precoCusto": 25.50,
                    "precoVenda": 45.90,
                    "categoria": "FILTROS",
                    "ativo": true
                }
                """.formatted(unique, unique);
    }

    public static String pecaComDadosInvalidos() {
        return """
                {
                    "nome": "",
                    "descricao": "",
                    "codigoFabricante": "",
                    "marca": "",
                    "idsModelosCompativeis": [],
                    "quantidadeEstoque": -1,
                    "precoCusto": -10.50,
                    "precoVenda": -25.90,
                    "categoria": "",
                    "ativo": null
                }
                """;
    }

    public static String pecaComCamposVazios() {
        return """
                {
                    "nome": null,
                    "descricao": null,
                    "codigoFabricante": null,
                    "marca": null,
                    "idsModelosCompativeis": null,
                    "quantidadeEstoque": null,
                    "precoCusto": null,
                    "precoVenda": null,
                    "categoria": null,
                    "ativo": null
                }
                """;
    }

    public static String pecaParaAtualizar() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Pastilha de Freio %s",
                    "descricao": "Pastilha de freio dianteira",
                    "codigoFabricante": "PF-%s",
                    "marca": "TRW",
                    "idsModelosCompativeis": [1],
                    "quantidadeEstoque": 8,
                    "precoCusto": 35.00,
                    "precoVenda": 65.00,
                    "categoria": "FREIOS",
                    "ativo": true
                }
                """.formatted(unique, unique);
    }

    public static String pecaParaDeletar() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Vela de Ignição %s",
                    "descricao": "Vela de ignição NGK padrão",
                    "codigoFabricante": "VI-%s",
                    "marca": "NGK",
                    "idsModelosCompativeis": [1],
                    "quantidadeEstoque": 4,
                    "precoCusto": 12.50,
                    "precoVenda": 22.90,
                    "categoria": "IGNICAO",
                    "ativo": true
                }
                """.formatted(unique, unique);
    }

    public static String pecaParaCicloCompleto() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Correia Dentada %s",
                    "descricao": "Correia dentada para motor 1.6",
                    "codigoFabricante": "CD-%s",
                    "marca": "Gates",
                    "idsModelosCompativeis": [1],
                    "quantidadeEstoque": 3,
                    "precoCusto": 85.00,
                    "precoVenda": 150.00,
                    "categoria": "MOTOR",
                    "ativo": true
                }
                """.formatted(unique, unique);
    }

    public static String atualizacaoCompleta() {
        return """
                {
                    "qtd": 15,
                    "precoCusto": 30.00,
                    "precoVenda": 55.00,
                    "ativa": true
                }
                """;
    }

    public static String atualizacaoInvalida() {
        return """
                {
                    "qtd": -5,
                    "precoCusto": -15.00,
                    "precoVenda": -30.00,
                    "ativa": null
                }
                """;
    }
}