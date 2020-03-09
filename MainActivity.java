package com.studio.jokenpo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface.OnShowListener;
import android.view.View.OnClickListener;

import java.util.Random;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements OnShowListener, OnClickListener, PassarPrincipal{

    private ImageView imagemAleatoria;
    private ImageView imagemPedra;
    private ImageView imagemPapel;
    private ImageView imagemTesoura;

    private TextView jogadas;
    private TextView qtInformada;
    private TextView placarJogador;
    private TextView placarMaquina;
    private TextView placarEmpate;
    private  TextView Segundos;

    private final String Pedra = "pedra";
    private final String Papel = "papel";
    private final String Tesoura = "tesoura";


    int pontosJogador = 0, pontosMaquina = 0, pontosEmpate = 0;
    int rodadas = 1, quantidade = 0;

    private SharedPreferences armazenar;
    private AlertDialog dialog;
    private EditText valorInformado;

    private final String Armazenamento_Jogadas = "Jogadas";
    private final String Armazenamento_qtdInformada = "qtdInformada";
    private final String Armazenamento_Jogador = "Jogador";
    private final String Armazenamento_Maquina = "Maquina";
    private final String Armazenamneto_Empate = "Empate";


    private MyCountDownTimer tempo;
    private final long TempoInicial = (4 * 1000);
    private final long TempoDecorrido = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemAleatoria = findViewById(R.id.imgAleatorio);
        imagemPedra = findViewById(R.id.imgPedra);
        imagemPapel = findViewById(R.id.imgPapel);
        imagemTesoura = findViewById(R.id.imgTesoura);

        jogadas = findViewById(R.id.lblJogadas);
        qtInformada = findViewById(R.id.lblqtdInformado);
        placarJogador = findViewById(R.id.txtPlacarJogador);
        placarMaquina = findViewById(R.id.txtPlacarMaquina);
        placarEmpate = findViewById(R.id.txtPlacarEmpate);
        Segundos = findViewById(R.id.txtSegundos);

        armazenar = getPreferences(MODE_PRIVATE);

        Dialogo();
        tempo = new MyCountDownTimer(this, Segundos, imagemPedra, imagemPapel, imagemTesoura, imagemAleatoria, TempoInicial, TempoDecorrido);

        imagemPedra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                ImagemEscolhida(Pedra);

                imagemPedra.setEnabled(false);
                imagemPapel.setVisibility(View.INVISIBLE);
                imagemTesoura.setVisibility(View.INVISIBLE);

                setValor(MainActivity.this,jogadas);

                tempo.start();

            }
        });

        imagemPapel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagemEscolhida(Papel);

                imagemPapel.setEnabled(false);
                imagemPedra.setVisibility(View.INVISIBLE);
                imagemTesoura.setVisibility(View.INVISIBLE);

                setValor(MainActivity.this,jogadas);

                tempo.start();

            }
        });

        imagemTesoura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagemEscolhida(Tesoura);

                imagemTesoura.setEnabled(false);
                imagemPedra.setVisibility(View.INVISIBLE);
                imagemPapel.setVisibility(View.INVISIBLE);

                setValor(MainActivity.this,jogadas);

                tempo.start();

            }
        });

    }


    private void ImagemEscolhida(String escolha){

        String resultado = MyRandom.getTexto(Pedra,Papel,Tesoura);

        setImagem(resultado);
        Pontuacao(escolha,resultado);

        placarJogador.setText(String.valueOf(pontosJogador));
        placarMaquina.setText(String.valueOf(pontosMaquina));
        placarEmpate.setText(String.valueOf(pontosEmpate));

        rodadas++;

    }

    private void setImagem(String resultado) {

        if (resultado.equals(Pedra))
            imagemAleatoria.setImageResource(R.drawable.pedra);
        else if(resultado.equals(Papel))
            imagemAleatoria.setImageResource(R.drawable.papel);
        else
            imagemAleatoria.setImageResource(R.drawable.tesoura);
    }


    public void Resultado(){
        String mensagemResultado;

        if(pontosJogador > pontosMaquina && pontosJogador >= pontosEmpate)
            mensagemResultado = "Parabéns, você ganhou!";
        else if(pontosMaquina > pontosJogador && pontosMaquina >= pontosEmpate)
            mensagemResultado = "Que pena, você perdeu";
        else
            mensagemResultado = "Jogo empatado";

        MensagemConfirmacao(mensagemResultado);

    }

    public void MensagemConfirmacao(String mensagemResultado){

        final int pJogador = pontosJogador;
        final int pMaquina = pontosMaquina;
        final int pEmpate = pontosEmpate;

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("RESULTADO");
        builder.setMessage(mensagemResultado.toUpperCase());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(MainActivity.this, ResultadoActivity.class);
                intent.putExtra(Armazenamento_Jogador,pJogador);
                intent.putExtra(Armazenamento_Maquina,pMaquina);
                intent.putExtra(Armazenamneto_Empate,pEmpate);
                startActivity(intent);
            }
        });

        pontosJogador = 0;
        pontosMaquina = 0;
        pontosEmpate = 0;
        quantidade = 0;
        rodadas = 1;

        jogadas.setText(String.valueOf(rodadas) + " /");
        qtInformada.setText("0");
        placarJogador.setText(String.valueOf(pontosJogador));
        placarMaquina.setText(String.valueOf(pontosMaquina));
        placarEmpate.setText(String.valueOf(pontosEmpate));

        AlertDialog dialog = builder.create();
        Dialogo();
        dialog.show();

    }

    private void Pontuacao(String escolha, String resultado){

        int pJogador = pontosJogador;
        int pMaquina = pontosMaquina;
        int pEmpate = pontosEmpate;

        switch (escolha){
            case Pedra:

                if (resultado.equals(Pedra)){
                    pontosEmpate++;
                    break;
                }

                if (resultado.equals(Tesoura))
                    pontosJogador++;
                else
                    pontosMaquina++;

                break;
            case Papel:

                if (resultado.equals(Papel)){
                    pontosEmpate++;
                    break;
                }

                if(resultado.equals(Pedra))
                    pontosJogador++;
                else
                    pontosMaquina++;

                break;
            case Tesoura:

                if (resultado.equals(Tesoura)){
                    pontosEmpate++;
                    break;
                }

                if(resultado.equals(Papel))
                    pontosJogador++;
                else
                    pontosMaquina++;

                break;
            default:
        }

        MensagemPonto(pJogador,pMaquina,pEmpate);
    }

    private void MensagemPonto(int pJogador, int pMaquina, int pEmpate){

        if (pJogador != pontosJogador)
            Toast.makeText(this,"Ponto do jagador",Toast.LENGTH_SHORT).show();
        else if(pMaquina != pontosMaquina)
            Toast.makeText(this,"Ponto da maquina",Toast.LENGTH_SHORT).show();
        else if(pEmpate != pontosEmpate)
            Toast.makeText(this,"Empate",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = armazenar.edit();
        editor.putInt(Armazenamento_Jogadas,rodadas);
        editor.putInt(Armazenamento_qtdInformada,quantidade);
        editor.putInt(Armazenamento_Jogador,pontosJogador);
        editor.putInt(Armazenamento_Maquina,pontosMaquina);
        editor.putInt(Armazenamneto_Empate,pontosEmpate);
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        jogadas.setText(String.valueOf(armazenar.getInt(Armazenamento_Jogadas,0)) + " /");
        qtInformada.setText(String.valueOf(armazenar.getInt(Armazenamento_qtdInformada,0)));
        placarJogador.setText(String.valueOf(armazenar.getInt(Armazenamento_Jogador,0)));
        placarMaquina.setText(String.valueOf(armazenar.getInt(Armazenamento_Maquina,0)));
        placarEmpate.setText(String.valueOf(armazenar.getInt(Armazenamneto_Empate,0)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(tempo == null)
            tempo.cancel();

        armazenar.edit().clear();

    }

    private void Dialogo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("QUANTIDADE DE JOGADAS");
        valorInformado = new EditText(this);
        valorInformado.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(valorInformado);

        builder.setPositiveButton("Confirmar", null);

        dialog = builder.create();
        dialog.setOnShowListener(this);
        dialog.show();

    }

    @Override
    public void onShow(DialogInterface d) {
        Button botao = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        botao.setId(DialogInterface.BUTTON_POSITIVE);
        botao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         quantidade =  Integer.parseInt(valorInformado.getText().toString());

        if(quantidade != 0){
            qtInformada.setText(String.valueOf(quantidade));
            dialog.dismiss();
        }
        else
            Toast.makeText(MainActivity.this, "Por favor informe um valor válido", Toast.LENGTH_LONG).show();
    }


    @Override
    public void setValor(MainActivity principal, TextView jogadas) {
        tempo.setValor(principal,jogadas);
    }
}
