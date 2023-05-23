package br.com.uniamerica.estacionamento.config;

import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class Validacoes {
    public static boolean validaNome(String nome) {
        String nomeInserido = "^[a-zA-Z ]+$";
        return nome.matches(nomeInserido);
    }
    public static String formataNome(String nome) {
        nome = nome.toLowerCase();
        nome = Character.toUpperCase(nome.charAt(0)) + nome.substring(1);
        return nome;
    }
    public static boolean validaModelo(String nome) {
        String nomeInserido = "^[a-zA-Z0-9]+$";
        return nome.matches(nomeInserido);
    }
    public static boolean validaPlaca(String placa) {
        String placaBr = "^[A-Z]{3}-\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$";
        return placa.matches(placaBr);
    }
    public static boolean validaTelefone(String tel) {
        String telBR = "[1-9][0-9] [2-9]{2}[0-9]{3}[0-9]{4}";
        return tel.matches(telBR);
    }
    public boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11)){
            return(false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;

            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)){
                dig10 = '0';
            }
            else {
                dig10 = (char)(r + 48); // converte no respectivo caractere numerico
            }

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;

            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);

            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            }
            else {
                dig11 = (char)(r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return(true);
            }
            else {
                return(false);
            }
        }
        catch (InputMismatchException erro) {
            return(false);
        }
    }
    public String imprimeCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }
}
