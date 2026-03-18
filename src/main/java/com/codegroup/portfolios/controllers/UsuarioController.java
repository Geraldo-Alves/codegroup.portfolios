package com.codegroup.portfolios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegroup.portfolios.dto.Response;
import com.codegroup.portfolios.repositories.UsuarioRepository;

@RestController
@RequestMapping("/admin/usuario")
public class UsuarioController extends BaseController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public Response findAll() {
        return super.success(usuarioRepository.findAll());
    }

}