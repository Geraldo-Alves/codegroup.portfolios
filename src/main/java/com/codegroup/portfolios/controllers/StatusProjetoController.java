package com.codegroup.portfolios.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegroup.portfolios.dto.Response;
import com.codegroup.portfolios.repositories.StatusProjetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/root/status-projeto") 
public class StatusProjetoController extends BaseController { 

    @Autowired StatusProjetoRepository statusProjetoRepository;

    @GetMapping
    public Response findAll() {
        return super.success(statusProjetoRepository.findAll());
    }
    

}