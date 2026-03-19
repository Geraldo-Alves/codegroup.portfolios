package com.codegroup.portfolios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegroup.portfolios.dto.Response;
import com.codegroup.portfolios.entities.Usuario;
import com.codegroup.portfolios.repositories.UsuarioRepository;
import com.codegroup.portfolios.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public Response register(@RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

        return super.success("Usuário criado com sucesso");
    }

    @PostMapping("/login")
    public Response login(@RequestBody Usuario request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());

        return super.success(token);
    }
}
