package br.com.alexsdm.postech.oficina.integrados.servicos;

public class JsonPayloadsServico {

    public static String login() {
        return """
                {
                    "username": "admin",
                    "password": "admin"
                }
                """;
    }

    public static String servicoValido() {
        return """
                {
                    "nome": "Troca de Óleo",
                    "descricao": "Serviço de troca de óleo completo",
                    "preco": 150.0,
                    "duracaoEstimada": 30,
                    "categoria": "Manutenção"
                }
                """;
    }

    public static String atualizacaoValida() {
        return """
                {
                    "nome": "Troca de Óleo Premium",
                    "descricao": "Troca de óleo com filtro incluso",
                    "preco": 200.0,
                    "duracaoEstimada": 45,
                    "categoria": "Manutenção Premium"
                }
                """;
    }
}
