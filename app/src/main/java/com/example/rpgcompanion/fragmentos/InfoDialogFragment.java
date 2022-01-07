package com.example.rpgcompanion.fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InfoDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Componentes");
        builder.setMessage("Giovanna Karla de Macedo Felix;\n" +
                "João Gabriel Reis Saraiva de Andrade;\n" +
                "João Vítor Souto dos Santos;\n" +
                "José Vitor de Freitas Cunha.");

        builder.setPositiveButton("Ver Projeto",this);
        builder.setNegativeButton("Sair",this);

        return builder.create();

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Activity activity = getActivity();

        if(activity instanceof AoClicarEmInfo){
            AoClicarEmInfo listener = (AoClicarEmInfo) activity;
            listener.aoClicar(which);
        }
    }

    public interface AoClicarEmInfo{
        public void aoClicar(int botao);
    }

}
