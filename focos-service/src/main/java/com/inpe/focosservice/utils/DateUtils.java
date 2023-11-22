package com.inpe.focosservice.utils;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe de utilidades de Data
 *
 * @author Otavio Abreu dos Santos Silva
 * @version 1.0
 * @since 2023-11-21
 */
@Component
public class DateUtils {
    /**
     * Metodo de validacao padrao de datas, retornando true caso
     * tudo esteja nos conformes
     *
     * @param data_inicio Data passada por parametro via request na variavel data_inicio, por padrao ontem
     * @param data_fim Data passada por parametro via request na variavel data_fim, por padrao hoje
     * @return true caso tudo esteja nos conformes, false caso algum procedimento nao passe
     */
    public static boolean dataIsValid(
            LocalDate data_inicio,
            LocalDate data_fim
    ) {
        if (data_inicio.isAfter(data_fim)) {
            // Caso de data inicio maior que data fim
            return false;
        }

        if (data_fim.isAfter(LocalDate.now())) {
            // Caso de data fim maior que hoje
            return false;
        }

        return true;
    }

    /**
     * Metodo padrao de normalizacao de datas, caso sejam nulas
     * entrega os valores padroes de data
     *
     * @param data_inicio Data passada por parametro via request na variavel data_inicio, por padrao ontem
     * @param data_fim Data passada por parametro via request na variavel data_fim, por padrao hoje
     * @return Array com dois objetos de data sendo a posicao 0 o valor de data_inicio e a posicao 1 o data_fim
     */
    public static LocalDate[] standardDateProcedure(
            @Nullable LocalDate data_inicio,
            @Nullable LocalDate data_fim
    ) {
        // Se a data for nula seta o valor padrao
        LocalDate[] datas = new LocalDate[2];

        if (data_inicio == null) {
            data_inicio = LocalDate.now().minusDays(1);
        }

        if (data_fim == null) {
            data_fim = LocalDate.now();
        }

        datas[0] = data_inicio;
        datas[1] = data_fim;

        return datas;
    }
}
