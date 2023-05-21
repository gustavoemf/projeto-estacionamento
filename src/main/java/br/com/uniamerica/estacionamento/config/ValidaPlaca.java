package br.com.uniamerica.estacionamento.config;

import org.springframework.stereotype.Component;

@Component
public class ValidaPlaca {
    public static boolean validaPlaca(String placa) {
        String placaBr = "^[A-Z]{3}-\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$";
        return placa.matches(placaBr);
    }
}
