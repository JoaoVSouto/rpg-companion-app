package com.example.rpgcompanion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.rpgcompanion.fragmentos.FichaDetalheFragment;
import com.example.rpgcompanion.fragmentos.FichaListaFragment;
import com.example.rpgcompanion.model.Ficha;

public class MainActivity extends AppCompatActivity implements FichaListaFragment.AoClicarNaFicha {

    private FichaListaFragment fichaListaFragment;
    private FragmentManager mFragmentManager;
    private Button btnCriarFicha;

    private final static int CRIACAO_FICHA_REQUEST_CODE = 1;

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
                startActivityForResult(intent, CRIACAO_FICHA_REQUEST_CODE);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CRIACAO_FICHA_REQUEST_CODE && data != null) {
            Ficha ficha = (Ficha) data.getSerializableExtra("ficha");
            fichaListaFragment.adicionar(ficha);
            Toast.makeText(getApplicationContext(), "Ficha criada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clicouNaFicha(Ficha ficha) {
        Intent it = new Intent(this, FichaDetalheActivity.class);
        it.putExtra(FichaDetalheFragment.FICHA, ficha);
        startActivity(it);
    }
}