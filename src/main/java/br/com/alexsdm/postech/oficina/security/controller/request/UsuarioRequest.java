package br.com.alexsdm.postech.oficina.security.controller.request;

public record UsuarioRequest(String username, String password, String role) {
}
