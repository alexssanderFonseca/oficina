package br.com.alexsdm.postech.oficina.integrados.cliente;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class JsonPayloads {

    private static final AtomicLong counter = new AtomicLong(1);
    private static final Random random = new Random();

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

    public static String veiculoModelo() {
        String unique = getUniqueId();
        return """
                {
                    "marca": "Toyota",
                    "modelo": "Corolla_%s",
                    "anoInicio": 2010,
                    "anoFim": 2020,
                    "tipo": "SEDAN"
                }
                """.formatted(unique);
    }

    public static String clienteValido() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "João",
                    "sobrenome": "Silva",
                    "cpfCnpj": "68058384011",
                    "email": "joao.silva.%s@email.com",
                    "telefone": "34999%s",
                    "endereco": {
                        "rua": "Rua das Flores",
                        "numero": "123",
                        "complemento": "Apt 101",
                        "bairro": "Centro",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)));
    }

    public static String clienteCompletoComVeiculo(Long veiculoModeloId) {
        String unique = getUniqueId();
        return """
                {
                    "nome": "João",
                    "sobrenome": "Silva",
                    "cpfCnpj": "83876990041",
                    "email": "joao.completo.%s@email.com",
                    "telefone": "34999%s",
                    "endereco": {
                        "rua": "Rua das Flores",
                        "numero": "123",
                        "complemento": "Apt 101",
                        "bairro": "Centro",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    },
                    "veiculo": {
                        "veiculoModeloId": %d,
                        "placa": "COM3I3%s",
                        "cor": "Azul",
                        "ano": "2020"
                    }
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)), veiculoModeloId, String.format("%01d", Long.parseLong(unique)));
    }

    public static String clienteSemVeiculo() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Maria",
                    "sobrenome": "Santos",
                    "cpfCnpj": "38659021037",
                    "email": "maria.santos.%s@email.com",
                    "telefone": "34999%s",
                    "endereco": {
                        "rua": "Av. Brasil",
                        "bairro": "Jardim América",
                        "numero": "456",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38408000"
                    }
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)));
    }

    public static String clienteComDadosInvalidos() {
        return """
                {
                    "nome": "",
                    "sobrenome": "",
                    "cpfCnpj": "123",
                    "email": "email-invalido",
                    "telefone": "123",
                    "endereco": {
                        "rua": "",
                        "numero": "",
                        "bairro": "",
                        "cidade": "",
                        "uf": "",
                        "cep": "123"
                    }
                }
                """;
    }


    public static String clienteComCpfInvalido() {
        return """
                {
                    "nome": "Cliente",
                    "sobrenome": "CPF Inválido",
                    "cpfCnpj": "123.456.789-00",
                    "email": "cpf.invalido@teste.com",
                    "telefone": "34999999999",
                    "endereco": {
                        "rua": "Rua Teste",
                        "numero": "123",
                        "bairro": "Bairro Teste",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """;
    }

    public static String clienteComEmailInvalido() {
        return """
                {
                    "nome": "Cliente",
                    "sobrenome": "Email Inválido",
                    "cpfCnpj": "47484360007",
                    "email": "email-sem-arroba",
                    "telefone": "34999999999",
                    "endereco": {
                        "rua": "Rua Teste",
                        "numero": "123",
                        "bairro": "Bairro Teste",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """;
    }

    public static String clienteParaBusca() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Cliente",
                    "sobrenome": "Para Busca",
                    "cpfCnpj": "57500243006",
                    "email": "cliente.busca.%s@teste.com",
                    "telefone": "34999%s",
                    "endereco": {
                        "rua": "Rua da Busca",
                        "numero": "100",
                        "bairro": "Centro",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)));
    }

    public static String clienteParaDeletar() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Cliente",
                    "sobrenome": "Para Deletar",
                    "cpfCnpj": "56377470088",
                    "email": "cliente.deletar.%s@teste.com",
                    "telefone": "34999%s",
                    "endereco": {
                        "rua": "Rua da Deleção",
                        "numero": "200",
                        "bairro": "Centro",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)));
    }

    public static String clienteParaAtualizar() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Cliente",
                    "sobrenome": "Para Atualizar",
                    "cpfCnpj": "99738677009",
                    "email": "cliente.atualizar.%s@teste.com",
                    "telefone": "34999%s",
                    "endereco": {
                        "rua": "Rua da Atualização",
                        "numero": "300",
                        "bairro": "Centro",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)));
    }

    public static String atualizacaoCompleta() {
        String unique = getUniqueId();
        return """
                {
                    "email": "email.atualizado.%s@teste.com",
                    "telefone": "34988%s"
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)));
    }

    public static String atualizacaoEmail(String email) {
        return """
                {
                    "email": "%s"
                }
                """.formatted(email);
    }

    public static String atualizacaoTelefone(String telefone) {
        return """
                {
                    "telefone": "%s"
                }
                """.formatted(telefone);
    }

    public static String atualizacaoEmailInvalido() {
        return """
                {
                    "email": "email-sem-arroba-invalido"
                }
                """;
    }

    public static String clienteComEmail(String email, String cpf) {
        return """
                {
                    "nome": "Cliente",
                    "sobrenome": "Com Email",
                    "cpfCnpj": "83465838009",
                    "email": "%s",
                    "telefone": "34999999999",
                    "endereco": {
                        "rua": "Rua Email",
                        "numero": "123",
                        "bairro": "Centro",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """.formatted(email);
    }

    public static String veiculoValido(Long veiculoModeloId) {
        String unique = getUniqueId();
        return """
                {
                    "veiculoModeloId": %d,
                    "placa": "NET-5747",
                    "cor": "Branco",
                    "ano": "2022"
                }
                """.formatted(veiculoModeloId, String.format("%01d", Long.parseLong(unique)));
    }

    public static String veiculoComDadosInvalidos() {
        return """
                {
                    "veiculoModeloId": null,
                    "placa": "",
                    "cor": "",
                    "ano": "abc"
                }
                """;
    }

    public static String veiculoComPlaca(Long veiculoModeloId, String placa) {
        return """
                {
                    "veiculoModeloId": %d,
                    "placa": "%s",
                    "cor": "Preto",
                    "ano": "2021"
                }
                """.formatted(veiculoModeloId, placa);
    }

    public static String veiculoComModeloInexistente() {
        String unique = getUniqueId();
        return """
                {
                    "veiculoModeloId": 99999,
                    "placa": "INX3I3%s",
                    "cor": "Verde",
                    "ano": "2020"
                }
                """.formatted(String.format("%01d", Long.parseLong(unique)));
    }

    public static String clienteParaCicloCompleto() {
        String unique = getUniqueId();
        return """
                {
                    "nome": "Cliente",
                    "sobrenome": "Ciclo Completo",
                    "cpfCnpj": "67249037005",
                    "email": "cliente.ciclo.%s@teste.com",
                    "telefone": "34999%s",
                    "endereco": {
                        "rua": "Rua do Ciclo",
                        "numero": "400",
                        "bairro": "Centro",
                        "cidade": "Uberlândia",
                        "uf": "MG",
                        "cep": "38400000"
                    }
                }
                """.formatted(unique, String.format("%06d", Long.parseLong(unique)));
    }
}