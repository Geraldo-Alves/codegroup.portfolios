package com.codegroup.portfolios.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.codegroup.portfolios.utils.CalculoRiscoUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjetoResumoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataPrevisaoTermino;
    private LocalDate dataTermino;
    private BigDecimal orcamentoTotal;
    private String andamento;
    private String membroResponsavel;
    private String classificacaoRisco;

    public ProjetoResumoDTO(Long id, String nome, String descricao, LocalDate dataInicio,
            LocalDate dataPrevisaoTermino, LocalDate dataTermino, BigDecimal orcamentoTotal,
            String andamento, String membroResponsavel) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataPrevisaoTermino = dataPrevisaoTermino;
        this.dataTermino = dataTermino;
        this.orcamentoTotal = orcamentoTotal;
        this.andamento = andamento;
        this.membroResponsavel = membroResponsavel;
        this.classificacaoRisco = CalculoRiscoUtil.calcularClassificacaoRisco(orcamentoTotal, dataPrevisaoTermino, dataTermino);
    }

}
