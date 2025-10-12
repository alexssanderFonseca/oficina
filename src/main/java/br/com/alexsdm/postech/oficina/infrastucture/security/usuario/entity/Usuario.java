package br.com.alexsdm.postech.oficina.infrastucture.security.usuario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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

    public Usuario(String username, String password) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.username = username;
    }
}
