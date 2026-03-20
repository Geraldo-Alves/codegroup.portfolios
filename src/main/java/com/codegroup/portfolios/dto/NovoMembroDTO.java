package com.codegroup.portfolios.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NovoMembroDTO {

    @NotNull(message = "Atribuição é obrigatória")
    private Long idAtribuicao;
    private Long idPessoa;
    private String nome;
    private String cpf;
    private String celular;
    private String email;
    private List<Integer> perfis;

}
