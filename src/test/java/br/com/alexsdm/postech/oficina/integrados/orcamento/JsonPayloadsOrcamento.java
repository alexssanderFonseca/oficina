package br.com.alexsdm.postech.oficina.integrados.orcamento;

public class JsonPayloadsOrcamento {

    public static String login() {
        return """
                {
                    "username": "admin",
                    "password": "admin"
                }
                """;
    }

    public static String criarOrcamentoValido() {
        return """
                {
                    "cpfCnpjCliente": "12345678901",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440001",
                    "pecas": [
                        {
                            "pecaId": 100,
                            "qtd": 2
                        }
                    ],
                    "servicos": [100]
                }
                """;
    }

    public static String criarOrcamentoValido2() {
        return """
                {
                    "cpfCnpjCliente": "12345678901",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440002",
                    "pecas": [
                        {
                            "pecaId": 100,
                            "qtd": 1
                        }
                    ],
                    "servicos": [100]
                }
                """;
    }

    public static String criarOrcamentoInvalido() {
        return """
                {
                    "cpfCnpjCliente": "",
                    "veiculoId": null,
                    "pecas": [
                        {
                            "pecaId": null,
                            "qtd": -1
                        }
                    ],
                    "servicos": []
                }
                """;
    }

    public static String criarOrcamentoClienteInexistente() {
        return """
                {
                    "cpfCnpjCliente": "99999999999",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440001",
                    "pecas": [
                        {
                            "pecaId": 100,
                            "qtd": 1
                        }
                    ],
                    "servicos": [100]
                }
                """;
    }

    public static String criarOrcamentoVeiculoInexistente() {
        return """
                {
                    "cpfCnpjCliente": "12345678901",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440099",
                    "pecas": [
                        {
                            "pecaId": 100,
                            "qtd": 1
                        }
                    ],
                    "servicos": [100]
                }
                """;
    }

    public static String criarOrcamentoPecaInexistente() {
        return """
                {
                    "cpfCnpjCliente": "12345678901",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440001",
                    "pecas": [
                        {
                            "pecaId": 99999,
                            "qtd": 1
                        }
                    ],
                    "servicos": [100]
                }
                """;
    }
}