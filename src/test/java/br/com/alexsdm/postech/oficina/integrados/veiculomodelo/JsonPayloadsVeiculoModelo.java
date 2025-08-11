package br.com.alexsdm.postech.oficina.integrados.veiculomodelo;

public class JsonPayloadsVeiculoModelo {

    public static String login() {
        return """
                {
                    "username": "admin",
                    "password": "admin"
                }
                """;
    }

    public static String modeloValido() {
        return """
                {
                    "marca": "Toyota",
                    "modelo": "Corolla",
                    "anoInicio": 2020,
                    "anoFim": 2024,
                    "tipo": "Sedan"
                }
                """;
    }

    public static String modeloValido2() {
        return """
                {
                    "marca": "Toyota",
                    "modelo": "Etios",
                    "anoInicio": 2020,
                    "anoFim": 2024,
                    "tipo": "Sedan"
                }
                """;
    }


    public static String modeloInvalido() {
        return """
                {
                    "marca": "",
                    "modelo": "",
                    "anoInicio": null,
                    "anoFim": null,
                    "tipo": ""
                }
                """;
    }

    public static String atualizacaoValida() {
        return """
                {
                    "marca": "Toyota",
                    "modelo": "Corolla XEI",
                    "anoInicio": 2021,
                    "anoFim": 2025,
                    "tipo": "Sedan Premium"
                }
                """;
    }

    public static String atualizacaoInvalida() {
        return """
                {
                    "marca": "",
                    "modelo": "",
                    "anoInicio": null,
                    "anoFim": null,
                    "tipo": ""
                }
                """;
    }
}
