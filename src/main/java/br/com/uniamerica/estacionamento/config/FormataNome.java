package br.com.uniamerica.estacionamento.config;

import org.springframework.stereotype.Component;

@Component
public class FormataNome {
    public static String formataNome(String nome) {
        nome = nome.toLowerCase();
        nome = Character.toUpperCase(nome.charAt(0)) + nome.substring(1);
        return nome;
    }
}
