package br.com.uniamerica.estacionamento.config;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Calculos {
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
    public static /*LocalDateTime*/ LocalTime calculaTempoComDesconto(/*LocalDateTime*/ LocalTime tempo, /*LocalDateTime*/ LocalTime tempoDesconto){
        /*LocalDateTime*/ LocalTime tempoDescontado = tempo
                /*.minusYears(tempoDesconto.getYear())
                .minusMonths(tempoDesconto.getMonthValue())
                .minusDays(tempoDesconto.getDayOfMonth())*/
                .minusHours(tempoDesconto.getHour())
                .minusMinutes(tempoDesconto.getMinute())
                .minusSeconds(tempoDesconto.getSecond());
        return tempoDescontado;
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
    public static /*LocalDateTime*/ LocalTime calculaTempoGanhoDeDesconto(/*LocalDateTime*/ LocalTime tempoDesconto, /*LocalDateTime*/ LocalTime tempoGanhoDeDesconto){
        /*LocalDateTime*/ LocalTime tempoGanho = tempoDesconto
                /*.plusYears(tempoGanhoDeDesconto.getYear())
                .plusMonths(tempoGanhoDeDesconto.getMonthValue())
                .plusDays(tempoGanhoDeDesconto.getDayOfMonth())*/
                .plusHours(tempoGanhoDeDesconto.getHour())
                .plusMinutes(tempoGanhoDeDesconto.getMinute())
                .plusSeconds(tempoGanhoDeDesconto.getSecond());
        return tempoGanho;
    }
    public static /*LocalDateTime*/ LocalTime subtraiTempoPago(/*LocalDateTime*/ LocalTime tempoPago, /*LocalDateTime*/ LocalTime tempoParaDesconto){
        /*LocalDateTime*/ LocalTime tempoNovo = tempoPago
                /*.minusYears(tempoParaDesconto.getYear())
                .minusMonths(tempoParaDesconto.getMonthValue())
                .minusDays(tempoParaDesconto.getDayOfMonth())*/
                .minusHours(tempoParaDesconto.getHour())
                .minusMinutes(tempoParaDesconto.getMinute())
                .minusSeconds(tempoParaDesconto.getSecond());
        return tempoNovo;
    }
    public static /*LocalDateTime*/ LocalTime calculaTempoMulta(/*LocalDateTime*/ LocalTime movimentacao, /*LocalDateTime*/ LocalTime expediente){
        /*LocalDateTime*/ LocalTime tempoMultaTotal = expediente
                    /*.minusYears(movimentacao.getYear())
                    .minusMonths(movimentacao.getMonthValue())
                    .minusDays(movimentacao.getDayOfMonth())*/
                    .minusHours(movimentacao.getHour())
                    .minusMinutes(movimentacao.getMinute())
                    .minusSeconds(movimentacao.getSecond());
        return tempoMultaTotal;
    }
    public static /*LocalDateTime*/ LocalTime calculaTempoMultaTotal(/*LocalDateTime*/ LocalTime tempoMultaInicio, /*LocalDateTime*/ LocalTime tempoMultaFim){
        /*LocalDateTime*/ LocalTime tempoMultaTotal = tempoMultaInicio
                /*.plusYears(tempoMultaFim.getYear())
                .plusMonths(tempoMultaFim.getMonthValue())
                .plusDays(tempoMultaFim.getDayOfMonth())*/
                .plusHours(tempoMultaFim.getHour())
                .plusMinutes(tempoMultaFim.getMinute())
                .plusSeconds(tempoMultaFim.getSecond())
                ;
        return tempoMultaTotal;
    }
    public static /*LocalDateTime*/ BigDecimal calculaValorMulta(/*LocalDateTime*/ LocalTime tempoMulta, BigDecimal valorMinutoMulta){
        float multaHora = tempoMulta.getHour() * 60;
        float multaMinuto = tempoMulta.getMinute();
        float multaTotal = multaHora + multaMinuto;

        return valorMinutoMulta.multiply(BigDecimal.valueOf(multaTotal));
    }
    public static /*LocalDateTime*/ BigDecimal calculaValorNormal(/*LocalDateTime*/ LocalTime tempo, BigDecimal valorHora){
        float horas = tempo.getHour();
        float minutos = (float) tempo.getMinute() / 60;
        float total = horas + minutos;

        return valorHora.multiply(BigDecimal.valueOf(total));
    }
    public static /*LocalDateTime*/ BigDecimal calculaValorTotal(BigDecimal valorMulta, BigDecimal valorNormal){
        return valorMulta.add(valorNormal);
    }
}
