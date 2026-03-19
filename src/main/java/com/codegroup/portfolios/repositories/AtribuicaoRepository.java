package com.codegroup.portfolios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegroup.portfolios.entities.Atribuicao;

@Repository
public interface AtribuicaoRepository extends JpaRepository<Atribuicao, Long> {

    List<Atribuicao> findAll();

}
