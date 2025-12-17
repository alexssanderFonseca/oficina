package br.com.alexsdm.postech.oficina.security.usuario.service;

import br.com.alexsdm.postech.oficina.security.usuario.entity.Usuario;
import br.com.alexsdm.postech.oficina.security.usuario.repository.UsuarioRepository;
import br.com.alexsdm.postech.oficina.security.usuario.service.input.UsuarioInput;
import br.com.alexsdm.postech.oficina.security.usuario.service.output.UsuarioOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioOutput cadastrar(UsuarioInput input) {
        if (usuarioRepository.findByUsername(input.username()).isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }

        Usuario usuario = new Usuario(
                input.username(),
                new BCryptPasswordEncoder().encode(input.password())
        );

        usuario = usuarioRepository.save(usuario);

        return new UsuarioOutput(usuario.getId(),
                usuario.getUsername());
    }

    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
