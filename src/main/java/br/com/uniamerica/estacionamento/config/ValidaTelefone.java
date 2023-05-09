package br.com.uniamerica.estacionamento.config;

import org.springframework.stereotype.Component;

@Component
public class ValidaTelefone {
    public static boolean validaTelefone(String tel) {
        String telBR = "\\([1-9][0-9]\\) [2-9]{2}[0-9]{3}\\-[0-9]{4}";
        return tel.matches(telBR);
    }
}
