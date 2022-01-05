package com.example.rpgcompanion.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rpgcompanion.R;
import com.example.rpgcompanion.model.Ficha;

public class FichaDetalheFragment extends Fragment {

    TextView tvName;
    TextView tvRace;
    TextView tvClass;
    TextView tvForce;
    TextView tvDexterity;
    TextView tvConstitution;
    TextView tvIntelligence;

    public static final String TAG_DETALHE = "tagDetalhes";
    public static final String FICHA = "ficha";

    Ficha mFicha;

    public FichaDetalheFragment() {}

    public static FichaDetalheFragment novaInstancia(Ficha ficha) {
        FichaDetalheFragment fichaDetalheFragment = new FichaDetalheFragment();

        Bundle parametros = new Bundle();
        parametros.putSerializable(FICHA, ficha);

        fichaDetalheFragment.setArguments(parametros);

        return fichaDetalheFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFicha = (Ficha) getArguments().getSerializable(FICHA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_ficha_detalhe, container, false);

        tvName = layout.findViewById(R.id.tvName);
        tvRace = layout.findViewById(R.id.tvRace);
        tvClass = layout.findViewById(R.id.tvClass);
        tvForce = layout.findViewById(R.id.tvForce);
        tvDexterity = layout.findViewById(R.id.tvDexterity);
        tvConstitution = layout.findViewById(R.id.tvConstitution);
        tvIntelligence = layout.findViewById(R.id.tvIntelligence);

        if (mFicha != null) {
            tvName.setText(mFicha.getNome());
            tvRace.setText(mFicha.getRaca());
            tvClass.setText(mFicha.getClasse());
            tvForce.setText(String.valueOf(mFicha.getForca()));
            tvDexterity.setText(String.valueOf(mFicha.getDestreza()));
            tvConstitution.setText(String.valueOf(mFicha.getConstituicao()));
            tvIntelligence.setText(String.valueOf(mFicha.getInteligencia()));
        }

        return layout;
    }
}