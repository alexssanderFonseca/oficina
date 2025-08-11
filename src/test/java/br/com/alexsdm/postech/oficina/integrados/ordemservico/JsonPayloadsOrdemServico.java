package br.com.alexsdm.postech.oficina.integrados.ordemservico;

public class JsonPayloadsOrdemServico {

    public static String login() {
        return """
                {
                    "username": "admin",
                    "password": "admin"
                }
                """;
    }

    public static String criarOrdemServicoValida() {
        return """
                {
                    "cpfCnpj": "12345678901",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440001",
                    "orcamentoId": null
                }
                """;
    }

    public static String criarOrdemServicoValida2() {
        return """
                {
                    "cpfCnpj": "12345678901",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440002",
                    "orcamentoId": null
                }
                """;
    }

    public static String criarOrdemServicoInvalida() {
        return """
                {
                    "cpfCnpj": "",
                    "veiculoId": null,
                    "orcamentoId": null
                }
                """;
    }

    public static String finalizarDiagnosticoValido() {
        return """
                {
                    "idPecasNecessarias": [
                        {
                            "idPeca": 100,
                            "qtd": 2
                        }
                    ],
                    "idServicosNecessarios": [100]
                }
                """;
    }

    public static String finalizarDiagnosticoInvalido() {
        return """
                {
                    "idPecasNecessarias": [
                        {
                            "idPeca": null,
                            "qtd": -1
                        }
                    ],
                    "idServicosNecessarios": []
                }
                """;
    }

    public static String executarOrdemServicoValido() {
        return """
                {
                    "orcamentoId": 100
                }
                """;
    }

    public static String executarOrdemServicoInvalido() {
        return """
                {
                    "orcamentoId": null
                }
                """;
    }
}