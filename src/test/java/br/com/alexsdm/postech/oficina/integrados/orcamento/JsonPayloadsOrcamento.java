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
                    "cpfCnpjCliente": "24906627080",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440001",
                    "pecas": [
                        {
                            "pecaId": "a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d",
                            "quantidade": 2
                        }
                    ],
                    "servicosIds": ["b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e"]
                }
                """;
    }

    public static String criarOrcamentoValido2() {
        return """
                {
                    "cpfCnpjCliente": "24906627080",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440002",
                    "pecas": [
                        {
                            "pecaId": "a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d",
                            "quantidade": 1
                        }
                    ],
                    "servicosIds": ["b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e"]
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
                    "servicosIds": []
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
                            "pecaId": "a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d",
                            "qtd": 1
                        }
                    ],
                    "servicosIds": ["b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e"]
                }
                """;
    }

    public static String criarOrcamentoVeiculoInexistente() {
        return """
                {
                    "cpfCnpjCliente": "24906627080",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440099",
                    "pecas": [
                        {
                            "pecaId": "a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d",
                            "qtd": 1
                        }
                    ],
                    "servicosIds": ["b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e"]
                }
                """;
    }

    public static String criarOrcamentoPecaInexistente() {
        return """
                {
                    "cpfCnpjCliente": "24906627080",
                    "veiculoId": "550e8400-e29b-41d4-a716-446655440001",
                    "pecas": [
                        {
                            "pecaId": "99999999-9999-9999-9999-999999999999",
                            "qtd": 1
                        }
                    ],
                    "servicosIds": ["b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e"]
                }
                """;
    }
}