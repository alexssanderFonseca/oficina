package br.com.alexsdm.postech.oficina.cliente.entity.validation;

public class ValidadorPlacaVeiculo {

    private static final String PADRAO_ANTIGO = "^[A-Z]{3}-\\d{4}$";
    private static final String PADRAO_MERCOSUL = "^[A-Z]{3}\\d[A-Z]\\d{2}$";

    public static boolean isValida(String placa) {
        if (isNuloOuVazio(placa)) {
            return false;
        }

        var placaMaiuscula = placa.toUpperCase().trim();

        return validarPlacaAntiga(placaMaiuscula) || validarPlacaMercosul(placaMaiuscula);
    }

    private static boolean isNuloOuVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private static boolean validarPlacaAntiga(String placa) {
        return placa.matches(PADRAO_ANTIGO);
    }

    private static boolean validarPlacaMercosul(String placa) {
        return placa.matches(PADRAO_MERCOSUL);
    }
}
