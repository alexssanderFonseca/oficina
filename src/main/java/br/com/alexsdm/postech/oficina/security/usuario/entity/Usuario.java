package br.com.alexsdm.postech.oficina.security.usuario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
public class Usuario {

    @Id
    private UUID id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    public Usuario(String username, String password, String role) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.username = username;
        this.role = role;
    }

}









