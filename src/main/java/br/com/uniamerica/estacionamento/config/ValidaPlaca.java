package br.com.uniamerica.estacionamento.config;

import org.springframework.stereotype.Component;

@Component
public class ValidaPlaca {
    public static boolean validaPlaca(String placa) {
        String placaBr = "^[A-Z]{3}[0-9]{4}$";
        return placa.matches(placaBr);
    }
}
