package com.studio.jokenpo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    private TextView PontosJogador;
    private TextView PontosMaquina;
    private TextView PontosEmpate;
    private Button botaoOK;

    private final String Armazenamento_Jogador = "Jogador";
    private final String Armazenamento_Maquina = "Maquina";
    private final String Armazenamneto_Empate = "Empate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        PontosJogador = findViewById(R.id.txtResultJogador);
        PontosMaquina = findViewById(R.id.txtResultMaquina);
        PontosEmpate  =  findViewById(R.id.txtResultEmpate);
        botaoOK = findViewById(R.id.btnOk);


        Bundle bundle = getIntent().getExtras();

        PontosJogador.setText(String.valueOf(bundle.getInt(Armazenamento_Jogador)));
        PontosMaquina.setText(String.valueOf(bundle.getInt(Armazenamento_Maquina)));
        PontosEmpate.setText(String.valueOf(bundle.getInt(Armazenamneto_Empate)));

        botaoOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
