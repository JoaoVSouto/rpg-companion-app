package com.example.rpgcompanion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.google.android.material.snackbar.Snackbar;

public class AnotacoesAventuraActivity extends AppCompatActivity implements AnotacoesListaFragment.AoClicarNaAnotacao{

    FloatingActionButton fabAdd;
    ConstraintLayout constraintLayout;

    private FragmentManager mFragmentManager;
    private AnotacoesListaFragment anotacoesListaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes_aventura);
        mFragmentManager = getSupportFragmentManager();
        anotacoesListaFragment = (AnotacoesListaFragment) mFragmentManager.findFragmentById(R.id.fragmentAnotacoesLista);
        constraintLayout = findViewById(R.id.coordinatorLayout);
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
        dialog.setTitle("Confirmar Exclus??o");
        dialog.setMessage("Deseja excluir a anota????o: "+anotacao.getTitulo()+ " ?");
        dialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(new AnotacaoDAO(getApplicationContext()).delete(anotacao)){
                    Snackbar.make(constraintLayout, "Anota????o foi removida", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Anota????o foi removida", Toast.LENGTH_SHORT).show();
                    carregarAnotacoes();
                } else {
                    Snackbar.make(constraintLayout, "Erro ao deletar anota????o", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Erro ao deletar anota????o", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("N??o",null);
        dialog.create();
        dialog.show();
    }
}