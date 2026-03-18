package com.codegroup.portfolios.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codegroup.portfolios.entities.Usuario;
import com.codegroup.portfolios.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getSenha(),
                user.getPerfis().stream()
                        .map(perfil -> new SimpleGrantedAuthority(perfil.getNome()))
                        .toList()
        );
    }
}