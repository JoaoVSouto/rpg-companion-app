package com.example.rpgcompanion.fragmentos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rpgcompanion.R;


public class DadoDialogFragment extends DialogFragment {


    public static DadoDialogFragment newInstance(int lados, int resultado) {
        
        Bundle args = new Bundle();
        args.putInt("lados", lados);
        args.putInt("resultado", resultado);

        DadoDialogFragment fragment = new DadoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Resultado do D"+String.valueOf(getArguments().getInt("lados"))+":");
        builder.setMessage(String.valueOf(getArguments().getInt("resultado")));

        return builder.create();
    }
}