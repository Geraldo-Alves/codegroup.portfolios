package com.codegroup.portfolios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegroup.portfolios.entities.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Perfil findById(Integer id);
    List<Perfil> findAll();

}
