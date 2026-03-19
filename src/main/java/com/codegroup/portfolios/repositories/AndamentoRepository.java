package com.codegroup.portfolios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegroup.portfolios.entities.Andamento;

@Repository
public interface AndamentoRepository extends JpaRepository<Andamento, Long> {

    List<Andamento> findAll();

    Optional<Andamento> findByRefIdProjetoIdAndAtualTrue(Long projetoId);

}
