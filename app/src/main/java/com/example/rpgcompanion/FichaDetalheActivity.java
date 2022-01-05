package com.example.rpgcompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.rpgcompanion.fragmentos.FichaDetalheFragment;
import com.example.rpgcompanion.model.Ficha;

public class FichaDetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_detalhe);

        Intent it = getIntent();

        Ficha ficha = (Ficha) it.getExtras().getSerializable(FichaDetalheFragment.FICHA);

        FichaDetalheFragment fichaDetalheFragment = FichaDetalheFragment.novaInstancia(ficha);

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.detalhe, fichaDetalheFragment, FichaDetalheFragment.TAG_DETALHE);

        ft.commit();
    }
}