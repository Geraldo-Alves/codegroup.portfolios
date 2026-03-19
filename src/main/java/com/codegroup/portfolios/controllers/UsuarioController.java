package com.codegroup.portfolios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegroup.portfolios.dto.Response;
import com.codegroup.portfolios.dto.UsuarioDTO;
import com.codegroup.portfolios.repositories.UsuarioRepository;
import com.codegroup.portfolios.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/root/usuario")
public class UsuarioController extends BaseController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Response findAll() {
        return super.success(usuarioRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        return super.created(usuarioService.createUsuario(usuarioDTO));
    }

}