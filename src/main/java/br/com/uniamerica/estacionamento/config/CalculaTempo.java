package br.com.uniamerica.estacionamento.config;

import java.math.BigDecimal;
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
    public static /*LocalDateTime*/ LocalTime calculaTempoComDesconto(/*LocalDateTime*/ LocalTime tempo, /*LocalDateTime*/ LocalTime tempoGanhoDeDesconto){
        /*LocalDateTime*/ LocalTime tempoDescontado = tempo
                /*.minusYears(tempoGanhoDeDesconto.getYear())
                .minusMonths(tempoGanhoDeDesconto.getMonthValue())
                .minusDays(tempoGanhoDeDesconto.getDayOfMonth())*/
                .minusHours(tempoGanhoDeDesconto.getHour())
                .minusMinutes(tempoGanhoDeDesconto.getMinute())
                .minusSeconds(tempoGanhoDeDesconto.getSecond());
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
    public static boolean validaTempoPago (/*LocalDateTime*/ LocalTime tempoPago, /*LocalDateTime*/ LocalTime tempoParaDesconto){
        if(tempoPago.compareTo(tempoParaDesconto) == 0 || tempoPago.compareTo(tempoParaDesconto) > 0) {
            return true;
        } else {
            return false;
        }
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
    public static /*LocalDateTime*/ LocalTime calculaTempoMultaInicio(/*LocalDateTime*/ LocalTime entrada, /*LocalDateTime*/ LocalTime inicioExpediente){
        /*LocalDateTime*/ LocalTime tempoMultaTotal = entrada
                    /*.minusYears(inicioExpediente.getYear())
                    .minusMonths(inicioExpediente.getMonthValue())
                    .minusDays(inicioExpediente.getDayOfMonth())*/
                    .minusHours(inicioExpediente.getHour())
                    .minusMinutes(inicioExpediente.getMinute())
                    .minusSeconds(inicioExpediente.getSecond());
        return tempoMultaTotal;
    }
    public static /*LocalDateTime*/ LocalTime calculaTempoMultaSaida(/*LocalDateTime*/ LocalTime saida, /*LocalDateTime*/ LocalTime fimExpediente){
        /*LocalDateTime*/ LocalTime tempoMultaTotal = saida
                    /*.minusYears(fimExpediente.getYear())
                    .minusMonths(fimExpediente.getMonthValue())
                    .minusDays(fimExpediente.getDayOfMonth())*/
                    .minusHours(fimExpediente.getHour())
                    .minusMinutes(fimExpediente.getMinute())
                    .minusSeconds(fimExpediente.getSecond());
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
    public static /*LocalDateTime*/ BigDecimal calculaValorMultaInicio(/*LocalDateTime*/ LocalTime tempoMultaInicio, BigDecimal valorMinutoMulta){
        long multaHora = tempoMultaInicio.getHour();
        long multaMinuto = tempoMultaInicio.getMinute() / 60;
        long multaSegundos = tempoMultaInicio.getSecond() / 3600;
        long multaTotal = multaHora + multaMinuto + multaSegundos;

        BigDecimal valorMulta = valorMinutoMulta.multiply(BigDecimal.valueOf(multaTotal));
        return valorMulta;
    }
    public static /*LocalDateTime*/ BigDecimal calculaValorMultaSaida(/*LocalDateTime*/ LocalTime tempoMultaSaida, BigDecimal valorMinutoMulta){
        long multaHora = tempoMultaSaida.getHour();
        long multaMinuto = tempoMultaSaida.getMinute() / 60;
        long multaSegundos = tempoMultaSaida.getSecond() / 3600;
        long multaTotal = multaHora + multaMinuto + multaSegundos;

        BigDecimal valorMulta = valorMinutoMulta.multiply(BigDecimal.valueOf(multaTotal));
        return valorMulta;
    }
    public static /*LocalDateTime*/ BigDecimal calculaValorMultaTotal(/*LocalDateTime*/ LocalTime tempoMultaTotal, BigDecimal valorMinutoMulta){
        long multaHora = tempoMultaTotal.getHour();
        long multaMinuto = tempoMultaTotal.getMinute() / 60;
        long multaSegundos = tempoMultaTotal.getSecond() / 3600;
        long multaTotal = multaHora + multaMinuto + multaSegundos;

        BigDecimal valorMulta = valorMinutoMulta.multiply(BigDecimal.valueOf(multaTotal));
        return valorMulta;
    }
    public static /*LocalDateTime*/ BigDecimal calculaValorNormal(/*LocalDateTime*/ LocalTime tempoNormal, BigDecimal valorHora){
        long horas = tempoNormal.getHour();
        long minutos = tempoNormal.getMinute() / 60;
        long segundos = tempoNormal.getSecond() / 3600;
        long total = horas + minutos + segundos;

        BigDecimal valorNormal = valorHora.multiply(BigDecimal.valueOf(total));
        return valorNormal;
    }
    public static /*LocalDateTime*/ BigDecimal calculaValorTotal(BigDecimal valorMulta, BigDecimal valorNormal){
        BigDecimal valorTotal = valorMulta.add(valorNormal);
        return valorTotal;
    }
}
