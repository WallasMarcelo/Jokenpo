package com.studio.jokenpo;

import java.util.Random;

public class MyRandom{

    public static String getTexto(String Pedra, String Papel, String Tesoura){

        Random gerar = new Random();
        int numeroGerado = gerar.nextInt(3);

        if(numeroGerado == 0)
            return Pedra;
        else if(numeroGerado == 1)
            return Papel;
        else
            return Tesoura;

    }

}
