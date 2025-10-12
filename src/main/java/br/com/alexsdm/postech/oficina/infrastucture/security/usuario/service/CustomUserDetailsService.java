package br.com.alexsdm.postech.oficina.infrastucture.security.usuario.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = usuarioService.loadUserByUsername(username);
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles("ADMIN")
                .build();
    }
}
