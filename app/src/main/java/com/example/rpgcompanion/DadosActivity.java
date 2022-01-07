package com.example.rpgcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.rpgcompanion.fragmentos.DadoDialogFragment;

import java.util.Random;

public class DadosActivity extends AppCompatActivity {

    ImageButton btn4;
    ImageButton btn6;
    ImageButton btn8;
    ImageButton btn10;
    ImageButton btn12;
    ImageButton btn20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);

        btn4 = findViewById(R.id.btn4);
        btn6 = findViewById(R.id.btn6);
        btn8 = findViewById(R.id.btn8);
        btn10 = findViewById(R.id.btn10);
        btn12 = findViewById(R.id.btn12);
        btn20 = findViewById(R.id.btn20);

        View.OnClickListener viewClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn4:
                        rolarDado(4);
                        break;
                    case R.id.btn6:
                        rolarDado(6);
                        break;
                    case R.id.btn8:
                        rolarDado(8);
                        break;
                    case R.id.btn10:
                        rolarDado(10);
                        break;
                    case R.id.btn12:
                        rolarDado(12);
                        break;
                    case R.id.btn20:
                        rolarDado(20);
                        break;
                    default:
                        break;
                }

            }
        };

        btn4.setOnClickListener(viewClick);
        btn6.setOnClickListener(viewClick);
        btn8.setOnClickListener(viewClick);
        btn10.setOnClickListener(viewClick);
        btn12.setOnClickListener(viewClick);
        btn20.setOnClickListener(viewClick);
    }

    private void rolarDado(int lados){
        int resultado = new Random().nextInt(lados)+1;
        DadoDialogFragment dadoDialogFragment = new DadoDialogFragment().newInstance(lados,resultado);
        dadoDialogFragment.show(getSupportFragmentManager(),"DadoResultado");
    }
}