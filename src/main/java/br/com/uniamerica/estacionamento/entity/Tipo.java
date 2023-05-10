package br.com.uniamerica.estacionamento.entity;

public enum Tipo {
    CARRO(1, "Carro"),
    MOTO(2, "Moto"),
    VAN(3, "Van");

    public final int valorNumerico;
    public final String valorString;

    private Tipo(int valorNumerico, String valorString){
        this.valorNumerico=valorNumerico;
        this.valorString=valorString;
    }
}