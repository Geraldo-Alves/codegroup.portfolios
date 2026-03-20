package com.codegroup.portfolios.dto;

import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SessionAttributes
public class MembroResumoDTO {
    private Long id;
    private String nome;
    private String atribuicao;
    private String status;
    private Boolean gerenteResponsavel;
}
