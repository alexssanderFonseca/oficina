package br.com.alexsdm.postech.oficina.infrastucture.security.controller;

import br.com.alexsdm.postech.oficina.infrastucture.security.controller.request.AuthRequest;
import br.com.alexsdm.postech.oficina.infrastucture.security.controller.request.UsuarioRequest;
import br.com.alexsdm.postech.oficina.infrastucture.security.controller.response.AuthResponse;
import br.com.alexsdm.postech.oficina.infrastucture.security.jwt.JwtUtil;
import br.com.alexsdm.postech.oficina.infrastucture.security.usuario.service.UsuarioService;
import br.com.alexsdm.postech.oficina.infrastucture.security.usuario.service.input.UsuarioInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;


    @PostMapping("/logins")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
            authenticationManager.authenticate(authToken);

            var token = jwtUtil.gerarToken(authRequest.username());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            var input = new UsuarioInput(usuarioRequest.username(), usuarioRequest.password());
            var output = usuarioService.cadastrar(input);
            return ResponseEntity.status(HttpStatus.CREATED).body(output);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


}
