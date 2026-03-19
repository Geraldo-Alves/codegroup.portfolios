package com.codegroup.portfolios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegroup.portfolios.entities.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {

    List<Membro> findAll();

    List<Membro> findByRefIdProjetoId(Long idProjeto);

    Optional<Membro> findByRefIdProjetoIdAndRefIdPessoaId(Long idProjeto, Long idPessoa);

    List<Membro> findByRefIdPessoaId(Long idPessoa);
      
}
