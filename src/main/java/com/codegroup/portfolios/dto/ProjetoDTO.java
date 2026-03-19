package com.codegroup.portfolios.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjetoDTO {
 
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    // valida data
    @NotNull(message = "Data de Início é obrigatória")
    private LocalDate dataInicio;
    @NotNull(message = "Data de Previsão de Término é obrigatória")
    private LocalDate dataPrevisaoTermino;
    private LocalDate dataTermino;
    @NotNull(message = "Orçamento Total é obrigatório")
    private BigDecimal orcamentoTotal;
    @NotNull(message = "Gerente é obrigatório")
    private Long idMembroGerente;
    @NotNull(message = "Status Atual é obrigatório")
    private Long idStatusAtual;

}
