package com.example.rpgcompanion.fragmentos;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rpgcompanion.R;
import com.example.rpgcompanion.model.Anotacao;
import com.example.rpgcompanion.model.Ficha;

import java.util.ArrayList;
import java.util.List;


public class AnotacoesListaFragment extends ListFragment {

    List<Anotacao> mAnotacoes;
    ArrayAdapter<Anotacao> mAdapter;

    public AnotacoesListaFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAnotacoes = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = getActivity();

                if (activity instanceof AnotacoesListaFragment.AoClicarNaAnotacao) {
                    Anotacao anotacao = (Anotacao) parent.getItemAtPosition(position);

                    AnotacoesListaFragment.AoClicarNaAnotacao listener = (AnotacoesListaFragment.AoClicarNaAnotacao) activity;
                    listener.pressionouAnotacao(anotacao);
                }

                return true;
            }
        });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Activity activity = getActivity();

        if (activity instanceof AnotacoesListaFragment.AoClicarNaAnotacao) {
            Anotacao anotacao = (Anotacao) l.getItemAtPosition(position);

            AnotacoesListaFragment.AoClicarNaAnotacao listener = (AnotacoesListaFragment.AoClicarNaAnotacao) activity;
            listener.clicouNaAnotacao(anotacao);
        }
    }

    public void setarAnotacoes(List<Anotacao> anotacoes) {
        mAnotacoes.clear();
        for (int i = 0; i < anotacoes.size(); i++) {
            mAnotacoes.add(anotacoes.get(i));
        }
        mAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mAnotacoes
        );

        setListAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public interface AoClicarNaAnotacao {
        void clicouNaAnotacao(Anotacao anotacao);
        void pressionouAnotacao(Anotacao anotacao);
    }
}