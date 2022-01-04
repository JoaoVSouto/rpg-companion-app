package com.example.rpgcompanion.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.widget.ArrayAdapter;

import com.example.rpgcompanion.model.Ficha;

import java.util.ArrayList;
import java.util.List;

public class FichaListaFragment extends ListFragment {

    List<Ficha> mFichas;
    ArrayAdapter<Ficha> mAdapter;

    public FichaListaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFichas = carregaFichas();

        limpaBusca();
    }

    @NonNull
    private List<Ficha> carregaFichas() {
        List<Ficha> fichas = new ArrayList<>();

        fichas.add(new Ficha("Trogdor", "Troll", "Bárbaro", 9, 4, 5, 2));
        fichas.add(new Ficha("Jon Snow", "Humano", "Lutador", 6, 6, 6, 6));
        fichas.add(new Ficha("Ayla", "Elfo", "Mago", 2, 4, 5, 9));

        return fichas;
    }

    public void buscar(String newText) {
        if (newText == null || newText.trim().equals("")) {
            limpaBusca();
            return;
        }

        List<Ficha> fichasEncontradas = new ArrayList<>(mFichas);

        for (int i = fichasEncontradas.size() - 1; i >= 0; i--) {
            Ficha ficha = fichasEncontradas.get(i);

            if (!ficha.getNome().toUpperCase().contains(newText.toUpperCase())) {
                fichasEncontradas.remove(ficha);
            }
        }

        mAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                fichasEncontradas
        );

        setListAdapter(mAdapter);
    }

    public void limpaBusca() {
        mAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mFichas
        );

        setListAdapter(mAdapter);
    }

}