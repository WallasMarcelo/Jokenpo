package com.studio.jokenpo;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MyCountDownTimer extends CountDownTimer implements PassarPrincipal {
    private Context context;
    private TextView segundos;
    private TextView jogadas;

    private ImageView imgPedra;
    private ImageView imgPapel;
    private ImageView imgTesoura;
    private ImageView imgAleatoria;

    private MainActivity principal;

    int rodadas, quantidade;

    public MyCountDownTimer(Context context, TextView segundos, ImageView imgPedra, ImageView imgPapel, ImageView imgTesoura,
                                                       ImageView imgAleatoria, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.segundos = segundos;
        this.imgPedra = imgPedra;
        this.imgPapel = imgPapel;
        this.imgTesoura = imgTesoura;
        this.imgAleatoria = imgAleatoria;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.segundos.setText("Aguarde: " + getCorrectTimer(millisUntilFinished) + " segundos");

    }

    @Override
    public void onFinish() {
        this.segundos.setText("");

        this.imgPedra.setEnabled(true);
        this.imgPapel.setEnabled(true);
        this.imgTesoura.setEnabled(true);

        this.imgPedra.setVisibility(View.VISIBLE);
        this.imgPapel.setVisibility(View.VISIBLE);
        this.imgTesoura.setVisibility(View.VISIBLE);

        this.imgAleatoria.setImageResource(R.drawable.aguarde);

        if (rodadas <= quantidade)
            jogadas.setText(String.valueOf(rodadas) + " /");
        else
            principal.Resultado();
    }

    private String getCorrectTimer(long milisegundos) {
        int calendar = Calendar.SECOND;
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(milisegundos);

        String auxiliar = String.valueOf(calendario.get(calendar));
        return (auxiliar);

    }


    @Override
    public void setValor(MainActivity principal, TextView jogadas) {
        this.principal = principal;

        this.rodadas = principal.rodadas;
        this.quantidade = principal.quantidade;
        this.jogadas = jogadas;

    }
}
