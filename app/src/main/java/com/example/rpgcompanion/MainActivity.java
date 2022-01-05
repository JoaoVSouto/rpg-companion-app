package com.example.rpgcompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.rpgcompanion.fragmentos.FichaDetalheFragment;
import com.example.rpgcompanion.fragmentos.FichaListaFragment;
import com.example.rpgcompanion.model.Ficha;

public class MainActivity extends AppCompatActivity implements FichaListaFragment.AoClicarNaFicha {

    private FichaListaFragment fichaListaFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        fichaListaFragment = (FichaListaFragment) mFragmentManager.findFragmentById(R.id.fragmentoLista);
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
    public void clicouNaFicha(Ficha ficha) {
        Intent it = new Intent(this, FichaDetalheActivity.class);
        it.putExtra(FichaDetalheFragment.FICHA, ficha);
        startActivity(it);
    }
}