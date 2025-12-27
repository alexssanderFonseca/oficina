package br.com.alexsdm.postech.oficina.security.controller;

import br.com.alexsdm.postech.oficina.security.controller.request.AuthRequest;
import br.com.alexsdm.postech.oficina.security.controller.request.UsuarioRequest;
import br.com.alexsdm.postech.oficina.security.controller.response.AuthResponse;
import br.com.alexsdm.postech.oficina.security.jwt.JwtUtil;
import br.com.alexsdm.postech.oficina.security.usuario.service.UsuarioService;
import br.com.alexsdm.postech.oficina.security.usuario.service.input.UsuarioInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.net.URI;
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


    @Operation(summary = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/logins")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
            var authentication = authenticationManager.authenticate(authToken);
            var role = authentication.getAuthorities().stream().findFirst().orElseThrow().getAuthority();
            var token = jwtUtil.gerarToken(authRequest.username(), role);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Cadastra um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Usuário já existe")
    })
    @PostMapping("/usuarios")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        try {
            var input = new UsuarioInput(usuarioRequest.username(), usuarioRequest.password(), usuarioRequest.role());
            var output = usuarioService.cadastrar(input);
            return ResponseEntity.created(URI.create("/usuarios/" + output.id())).body(output);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


}