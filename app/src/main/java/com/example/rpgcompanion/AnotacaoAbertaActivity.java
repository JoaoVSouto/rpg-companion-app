package com.example.rpgcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rpgcompanion.dao.AnotacaoDAO;
import com.example.rpgcompanion.model.Anotacao;

public class AnotacaoAbertaActivity extends AppCompatActivity {

    EditText etTitulo;
    EditText etAnotacoes;
    Button btnSalvar;

    Anotacao anotacaoAberta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacao_aberta);
        etTitulo = findViewById(R.id.etTitulo);
        etAnotacoes = findViewById(R.id.etAnotacoes);
        btnSalvar = findViewById(R.id.btnSalvar);
        Intent it  = getIntent();
        try {
            anotacaoAberta = (Anotacao) it.getExtras().getSerializable("anotacao");
            etTitulo.setText(anotacaoAberta.getTitulo());
            etAnotacoes.setText(anotacaoAberta.getAnotacoes());
        } catch (Exception e) {anotacaoAberta = null;}
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnotacaoDAO anotacaoDAO = new AnotacaoDAO(getApplicationContext());
                if(etTitulo.getText().toString().equals("") || etTitulo.getText().toString().length() > 29){
                    Toast.makeText(getApplicationContext(), "Título inválido.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(etAnotacoes.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Descrição inválida.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(anotacaoAberta != null){
                    anotacaoAberta.setTitulo(etTitulo.getText().toString());
                    anotacaoAberta.setAnotacoes(etAnotacoes.getText().toString());
                    if(anotacaoDAO.update(anotacaoAberta)){
                        Toast.makeText(getApplicationContext(), "Anotação atualizada.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Erro ao atualizar.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    anotacaoAberta = new Anotacao();
                    anotacaoAberta.setTitulo(etTitulo.getText().toString());
                    anotacaoAberta.setAnotacoes(etAnotacoes.getText().toString());
                    if(anotacaoDAO.create(anotacaoAberta)){
                        Toast.makeText(getApplicationContext(), "Anotação criada.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Erro ao criar.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}