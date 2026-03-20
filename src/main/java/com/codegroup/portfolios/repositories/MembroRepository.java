package com.codegroup.portfolios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codegroup.portfolios.dto.MembroResumoDTO;
import com.codegroup.portfolios.entities.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {

    List<Membro> findAll();

    List<Membro> findByRefIdProjetoId(Long idProjeto);

     @Query("""
        SELECT new com.codegroup.portfolios.dto.MembroResumoDTO(
            m.id, pe.nome, a.nome, m.status, m.gerenteResponsavel
        )
        FROM Membro m
        JOIN m.refIdProjeto p
        JOIN m.refIdPessoa pe
        JOIN m.refIdAtribuicao a
        WHERE m.status = 'A' AND p.id = :projetoId
    """)
    List<MembroResumoDTO> findResumoAtivoByProjeto(@Param("projetoId") Long idProjeto);

    Optional<Membro> findByRefIdProjetoIdAndRefIdPessoaId(Long idProjeto, Long idPessoa);

    List<Membro> findByRefIdPessoaId(Long idPessoa);
      
}
