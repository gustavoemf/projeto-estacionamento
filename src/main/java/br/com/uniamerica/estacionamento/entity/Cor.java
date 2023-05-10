package br.com.uniamerica.estacionamento.entity;

public enum Cor {
    BRANCO(1, "Branco"),
    PRATA(2, "Prata"),
    PRETO(3, "Preto"),
    CINZA(4, "Cinza"),
    VERMELHO(5, "Vermelho"),
    AZUL(6, "Azul"),
    BEGE(7, "Bege"),
    VERDE(8, "Verde");

    public final int valorNumerico;
    public final String valorString;

    private Cor(int valorNumerico, String valorString){
        this.valorNumerico=valorNumerico;
        this.valorString=valorString;
    }
}