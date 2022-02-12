package com.example.rpgcompanion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rpgcompanion.dao.AnotacaoDAO;
import com.example.rpgcompanion.fragmentos.AnotacoesListaFragment;
import com.example.rpgcompanion.model.Anotacao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AnotacoesAventuraActivity extends AppCompatActivity implements AnotacoesListaFragment.AoClicarNaAnotacao{

    FloatingActionButton fabAdd;

    private FragmentManager mFragmentManager;
    private AnotacoesListaFragment anotacoesListaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes_aventura);
        mFragmentManager = getSupportFragmentManager();
        anotacoesListaFragment = (AnotacoesListaFragment) mFragmentManager.findFragmentById(R.id.fragmentAnotacoesLista);

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(),AnotacaoAbertaActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onStart() {
        carregarAnotacoes();
        super.onStart();
    }

    private void carregarAnotacoes(){
        anotacoesListaFragment.setarAnotacoes(new AnotacaoDAO(getApplicationContext()).list());
    }

    @Override
    public void clicouNaAnotacao(Anotacao anotacao) {
        Intent it = new Intent(getApplicationContext(), AnotacaoAbertaActivity.class);
        it.putExtra("anotacao", anotacao);
        startActivity(it);
    }

    @Override
    public void pressionouAnotacao(Anotacao anotacao) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AnotacoesAventuraActivity.this);
        dialog.setTitle("Confirmar Exclusão");
        dialog.setMessage("Deseja excluir a anotação: "+anotacao.getTitulo()+ " ?");
        dialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(new AnotacaoDAO(getApplicationContext()).delete(anotacao)){
                    Toast.makeText(getApplicationContext(), "Anotação foi removida", Toast.LENGTH_SHORT).show();
                    carregarAnotacoes();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao deletar anotação", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Não",null);
        dialog.create();
        dialog.show();
    }
}