package com.codegroup.portfolios.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalculoRiscoUtil {

    public static String calcularClassificacaoRisco(BigDecimal orcamento, LocalDate dataPrevisaoTermino,
            LocalDate dataInicio) {


        // se data atual for após data de inicio, calcula o prazo a partir da data atual, caso contrário, calcula a partir da data de início
        LocalDate dataReferencia = (dataInicio != null && dataInicio.isAfter(LocalDate.now())) ? dataInicio : LocalDate.now();

        // prazo em meses a partir da data de referência
        long prazoEmMeses = dataReferencia.until(dataPrevisaoTermino, java.time.temporal.ChronoUnit.MONTHS);

        // orçamento até 100000 e prazo <= 3 meses: Baixo        
        if ((orcamento.compareTo(new BigDecimal(100000)) <= 0) && (prazoEmMeses <= 3)) {
            return "Baixo";
        }

        // orçamento entre 100001 e 500000 ou prazo entre 3 e 6 meses: Médio
        if ((orcamento.compareTo(new BigDecimal(100000)) > 0 && orcamento.compareTo(new BigDecimal(500000)) <= 0) ||
                (prazoEmMeses > 3 && prazoEmMeses <= 6)) {

            return "Médio";
        }

        // orçamento acima de 500000 ou prazo superior a 6 meses: Alto
        if ((orcamento.compareTo(new BigDecimal(500000)) > 0) || prazoEmMeses > 6) {
            return "Alto";
        }
        
        return "Não Classificado";
    }

}
