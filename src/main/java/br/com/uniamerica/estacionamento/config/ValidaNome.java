package br.com.uniamerica.estacionamento.config;

import org.springframework.stereotype.Component;

@Component
public class ValidaNome {
    public static boolean validaNome(String nome) {
        String nomeInserido = "^[a-zA-Z ]+$";
        return nome.matches(nomeInserido);
    }
}
