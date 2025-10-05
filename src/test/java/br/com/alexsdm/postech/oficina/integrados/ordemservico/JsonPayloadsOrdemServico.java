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
                    "cpfCnpj": "24906627080",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440001",
                    "orcamentoId": "c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f"
                }
                """;
    }

    public static String criarOrdemServicoValida2() {
        return """
                {
                    "cpfCnpj": "24906627080",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440002",
                    "orcamentoId": "c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f"
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
                            "idPeca": "a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d",
                            "qtd": 2
                        }
                    ],
                    "idServicosNecessarios": ["b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e"]
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
                    "orcamentoId": "c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f"
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