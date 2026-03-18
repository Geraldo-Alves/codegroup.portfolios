package com.codegroup.portfolios.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.codegroup.portfolios.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findAll();

}
