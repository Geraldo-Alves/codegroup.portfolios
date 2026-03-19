package com.codegroup.portfolios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegroup.portfolios.entities.StatusProjeto;

@Repository
public interface StatusProjetoRepository extends JpaRepository<StatusProjeto, Long> {

    List<StatusProjeto> findAll();

    Optional<StatusProjeto> findByStatusPaiId(Long idStatusPai);

}
