package com.example.rpgcompanion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.rpgcompanion.dao.FichaDAO;
import com.example.rpgcompanion.fragmentos.DadoDialogFragment;
import com.example.rpgcompanion.fragmentos.FichaDetalheFragment;
import com.example.rpgcompanion.fragmentos.FichaListaFragment;
import com.example.rpgcompanion.fragmentos.InfoDialogFragment;
import com.example.rpgcompanion.model.Ficha;

public class MainActivity extends AppCompatActivity implements FichaListaFragment.AoClicarNaFicha,
        InfoDialogFragment.AoClicarEmInfo {

    private FichaListaFragment fichaListaFragment;
    private FragmentManager mFragmentManager;
    private Button btnCriarFicha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        fichaListaFragment = (FichaListaFragment) mFragmentManager.findFragmentById(R.id.fragmentoLista);

        btnCriarFicha = findViewById(R.id.btnCriarFicha);
        btnCriarFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CriacaoFichaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.acao_pesquisar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Pesquisar...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fichaListaFragment.buscar(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dados_item:
                Intent it = new Intent(this,DadosActivity.class);
                startActivity(it);
                break;
            case R.id.anotacoes_item:
                Intent ait = new Intent(this,AnotacoesAventuraActivity.class);
                startActivity(ait);
                break;
            case R.id.acao_info:
                InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
                infoDialogFragment.show(mFragmentManager,"INFO");
                break;
            case R.id.bestiario:
                Intent it2 = new Intent(this,BestiarioActivity.class);
                startActivity(it2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clicouNaFicha(Ficha ficha) {
        Intent intent = new Intent(MainActivity.this, CriacaoFichaActivity.class);
        intent.putExtra("ficha", ficha);

        startActivity(intent);
    }

    @Override
    public void pressionouFicha(Ficha ficha) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Confirmar exclusão:");
        builder.setMessage("Deseja excluir a ficha: " + ficha.getNome() + "?");
        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FichaDAO fichaDAO = new FichaDAO(getApplicationContext());

                if (fichaDAO.delete(ficha)) {
                    Toast.makeText(getApplicationContext(), "Ficha removida!", Toast.LENGTH_SHORT).show();
                    carregarFichas();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao remover ficha...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Não", null);

        builder.create();
        builder.show();
    }

    @Override
    public void aoClicar(int botao) {
        if(botao == DialogInterface.BUTTON_POSITIVE) {
            Intent it = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/JoaoVSouto/rpg-companion-app"));
            startActivity(it);
        }
    }

    public void carregarFichas() {
        FichaDAO fichaDAO = new FichaDAO(getApplicationContext());
        fichaListaFragment.setarFichas(fichaDAO.list());
    }

    @Override
    protected void onStart() {
        this.carregarFichas();
        super.onStart();
    }
}