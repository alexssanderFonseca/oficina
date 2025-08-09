package br.com.alexsdm.postech.oficina.admin.cliente.entity.validation;

public class ValidadorCpfCnpj {

    public static boolean isValido(String cpfCnpj) {
        if (isNuloOuVazio(cpfCnpj)) {
            return false;
        }

        var somenteDigitos = extrairDigitos(cpfCnpj);

        if (!temTamanhoValido(somenteDigitos)) {
            return false;
        }

        if (isCpf(somenteDigitos)) {
            return validarCpf(somenteDigitos);
        } else if (isCnpj(somenteDigitos)) {
            return validarCnpj(somenteDigitos);
        }

        return false;
    }

    private static boolean isNuloOuVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private static String extrairDigitos(String valor) {
        return valor.replaceAll("\\D", "");
    }

    private static boolean temTamanhoValido(String valor) {
        return valor.length() == 11 || valor.length() == 14;
    }

    private static boolean isCpf(String valor) {
        return valor.length() == 11;
    }

    private static boolean isCnpj(String valor) {
        return valor.length() == 14;
    }

    private static boolean validarCpf(String cpf) {
        if (todosCaracteresIguais(cpf)) {
            return false;
        }

        int primeiroDigito = calcularDigitoCpf(cpf, 10);
        int segundoDigito = calcularDigitoCpf(cpf, 11);

        return cpf.endsWith("" + primeiroDigito + segundoDigito);
    }

    private static boolean validarCnpj(String cnpj) {
        if (todosCaracteresIguais(cnpj)) {
            return false;
        }

        int primeiroDigito = calcularDigitoCnpj(cnpj, 12);
        int segundoDigito = calcularDigitoCnpj(cnpj, 13);

        return cnpj.endsWith("" + primeiroDigito + segundoDigito);
    }

    private static boolean todosCaracteresIguais(String valor) {
        char primeiro = valor.charAt(0);
        for (int i = 1; i < valor.length(); i++) {
            if (valor.charAt(i) != primeiro) {
                return false;
            }
        }
        return true;
    }

    private static int calcularDigitoCpf(String cpf, int pesoInicial) {
        var soma = 0;
        for (int i = 0; i < pesoInicial - 1; i++) {
            soma += (cpf.charAt(i) - '0') * (pesoInicial - i);
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    private static int calcularDigitoCnpj(String cnpj, int posicaoDigito) {
        var pesos = (posicaoDigito == 12)
                ? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
                : new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        var soma = 0;
        for (var i = 0; i < pesos.length; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos[i];
        }
        var resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}
