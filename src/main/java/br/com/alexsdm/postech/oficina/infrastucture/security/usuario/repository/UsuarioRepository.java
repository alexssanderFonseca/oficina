package br.com.alexsdm.postech.oficina.infrastucture.security.usuario.repository;

import br.com.alexsdm.postech.oficina.infrastucture.security.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByUsername(String username);
}
