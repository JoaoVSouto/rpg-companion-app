package com.example.rpgcompanion.fragmentos;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        mFichas = new ArrayList<>();

        limpaBusca();
    }

    @Override
    public void onStart() {
        super.onStart();

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = getActivity();

                if (activity instanceof AoClicarNaFicha) {
                    Ficha ficha = (Ficha) parent.getItemAtPosition(position);

                    AoClicarNaFicha listener = (AoClicarNaFicha) activity;
                    listener.pressionouFicha(ficha);
                }

                return true;
            }
        });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Activity activity = getActivity();

        if (activity instanceof AoClicarNaFicha) {
            Ficha ficha = (Ficha) l.getItemAtPosition(position);

            AoClicarNaFicha listener = (AoClicarNaFicha) activity;
            listener.clicouNaFicha(ficha);
        }
    }

    public void setarFichas(List<Ficha> fichas) {
        mFichas.clear();
        for (int i = 0; i < fichas.size(); i++) {
            mFichas.add(fichas.get(i));
        }
        mAdapter.notifyDataSetChanged();
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

    public interface AoClicarNaFicha {
        void clicouNaFicha(Ficha ficha);
        void pressionouFicha(Ficha ficha);
    }

}