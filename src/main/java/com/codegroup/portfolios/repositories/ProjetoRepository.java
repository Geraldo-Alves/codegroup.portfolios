package com.codegroup.portfolios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codegroup.portfolios.dto.ProjetoResumoDTO;
import com.codegroup.portfolios.entities.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findAll();
    @Query("""
        SELECT new com.codegroup.portfolios.dto.ProjetoResumoDTO(
            p.id, p.nome, p.descricao, p.dataInicio, p.dataPrevisaoTermino, p.dataTermino, p.orcamentoTotal,
            s.nome,
            pe.nome
        )
        FROM Andamento a
        JOIN a.refIdProjeto p
        JOIN a.refIdStatus s
        JOIN a.refIdMembro m
        JOIN m.refIdPessoa pe
        JOIN m.refIdAtribuicao at
        WHERE a.atual = true AND s.id = :statusId
    """)
    List<ProjetoResumoDTO> findResumoByStatusAtual(@Param("statusId") Long statusId);

    @Query("""
        SELECT new com.codegroup.portfolios.dto.ProjetoResumoDTO(
            p.id, p.nome, p.descricao, p.dataInicio, p.dataPrevisaoTermino, p.dataTermino, p.orcamentoTotal,
            s.nome,
            pe.nome
        )
        FROM Andamento a
        JOIN a.refIdProjeto p
        JOIN a.refIdStatus s
        JOIN a.refIdMembro m
        JOIN m.refIdPessoa pe
        JOIN m.refIdAtribuicao at
        WHERE a.atual = true AND p.id = :projetoId
    """)
    List<ProjetoResumoDTO> findResumoById(@Param("projetoId") Long projetoId);

}
