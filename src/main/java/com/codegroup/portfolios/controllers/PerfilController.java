package com.codegroup.portfolios.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegroup.portfolios.dto.Response;
import com.codegroup.portfolios.repositories.PerfilRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/root/perfil") 
public class PerfilController extends BaseController {

    @Autowired
    PerfilRepository perfilRepository;

    @GetMapping
    public Response findAll() {
        return super.success(perfilRepository.findAll());
    }
    

}
