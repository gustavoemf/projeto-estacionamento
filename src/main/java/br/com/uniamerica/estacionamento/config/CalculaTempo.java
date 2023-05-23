package br.com.uniamerica.estacionamento.config;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CalculaTempo {
    public static /*LocalDateTime*/ LocalTime calculaTempo(/*LocalDateTime*/ LocalTime entrada, /*LocalDateTime*/ LocalTime saida){
        /*LocalDateTime*/ LocalTime tempo = saida
                /*.minusYears(entrada.getYear())
                .minusMonths(entrada.getMonthValue())
                .minusDays(entrada.getDayOfMonth())*/
                .minusHours(entrada.getHour())
                .minusMinutes(entrada.getMinute())
                .minusSeconds(entrada.getSecond());
        return tempo;
    }
    public static /*LocalDateTime*/ LocalTime calculaTempoPago(/*LocalDateTime*/ LocalTime tempoPago, /*LocalDateTime*/ LocalTime tempo){
        /*LocalDateTime*/ LocalTime tempoTotal = tempoPago
                /*.plusYears(tempo.getYear())
                .plusMonths(tempo.getMonthValue())
                .plusDays(tempo.getDayOfMonth())*/
                .plusHours(tempo.getHour())
                .plusMinutes(tempo.getMinute())
                .plusSeconds(tempo.getSecond());
        return tempoTotal;
    }
}
