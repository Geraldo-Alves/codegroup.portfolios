package com.codegroup.portfolios.services;

import org.springframework.stereotype.Service;

import com.codegroup.portfolios.dto.Usuario;

@Service
public class ServerService {

    public Usuario hello() {
        return new Usuario("jhon doe", "jhon.doe@example.com");
    }

    public Usuario error() {
        throw new RuntimeException("Exceção Genérica");
    }
}
