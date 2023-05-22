package br.com.uniamerica.estacionamento.config;

import org.springframework.stereotype.Component;

@Component
public class ValidaModelo {
    public static boolean validaModelo(String nome) {
        String nomeInserido = "^[a-zA-Z0-9]+$";
        return nome.matches(nomeInserido);
    }
}
