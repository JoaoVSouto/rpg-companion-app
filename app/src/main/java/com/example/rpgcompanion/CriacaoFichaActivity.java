package com.example.rpgcompanion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rpgcompanion.dao.FichaDAO;
import com.example.rpgcompanion.model.Ficha;

public class CriacaoFichaActivity extends AppCompatActivity {

    Spinner spnRace;
    Spinner spnClass;
    EditText etName;
    EditText etForce;
    EditText etDexterity;
    EditText etConstitution;
    EditText etIntelligence;
    Button btnCreate;
    TextView tvCreateEditTitle;

    Ficha fichaAtual;

    private static final String[] races = {"", "Troll", "Elfo", "Humano", "Anão", "Gnomo", "Ogro"};
    private static final String[] classes = {"", "Bárbaro", "Lutador", "Mago", "Feiticeiro", "Bardo", "Druida"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criacao_ficha);

        spnRace = findViewById(R.id.spnRace);
        ArrayAdapter<String> racesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, races);
        spnRace.setAdapter(racesAdapter);

        spnClass = findViewById(R.id.spnClass);
        ArrayAdapter<String> classesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classes);
        spnClass.setAdapter(classesAdapter);

        etName = findViewById(R.id.etName);
        etForce = findViewById(R.id.etForce);
        etDexterity = findViewById(R.id.etDexterity);
        etConstitution = findViewById(R.id.etConstitution);
        etIntelligence = findViewById(R.id.etIntelligence);
        btnCreate = findViewById(R.id.btnCreate);
        tvCreateEditTitle = findViewById(R.id.tvCreateEditTitle);

        Intent it = getIntent();

        try {
            fichaAtual = (Ficha) it.getExtras().getSerializable("ficha");
            tvCreateEditTitle.setText("Edição de ficha");
        } catch (Exception e) {
            tvCreateEditTitle.setText("Criação de ficha");
            fichaAtual = null;
        }

        if (fichaAtual != null) {
            etName.setText(fichaAtual.getNome());
            spnRace.setSelection(findIndex(races, fichaAtual.getRaca()));
            spnClass.setSelection(findIndex(classes, fichaAtual.getClasse()));
            etForce.setText(String.valueOf(fichaAtual.getForca()));
            etDexterity.setText(String.valueOf(fichaAtual.getDestreza()));
            etConstitution.setText(String.valueOf(fichaAtual.getConstituicao()));
            etIntelligence.setText(String.valueOf(fichaAtual.getInteligencia()));
        }

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreateFicha();
            }
        });

    }

    private int findIndex(String[] array, String string) {
        for (int i = 0; i < array.length; i++) {
            if (string.equals(array[i])) {
                return i;
            }
        }

        return 0;
    }

    private void createError(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(CriacaoFichaActivity.this).create();
        alertDialog.setTitle("Erro");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void handleCreateFicha() {
        try {
            String selectedName = etName.getText().toString();
            String selectedRace = spnRace.getSelectedItem().toString();
            String selectedClass = spnClass.getSelectedItem().toString();
            String forceString = etForce.getText().toString();
            int selectedForce = Integer.parseInt(forceString.equals("") ? "-1" : forceString);
            String dexterityString = etDexterity.getText().toString();
            int selectedDexterity = Integer.parseInt(dexterityString.equals("") ? "-1" : dexterityString);
            String constitutionString = etConstitution.getText().toString();
            int selectedConstitution = Integer.parseInt(constitutionString.equals("") ? "-1" : constitutionString);
            String intelligenceString = etIntelligence.getText().toString();
            int selectedIntelligence = Integer.parseInt(intelligenceString.equals("") ? "-1" : intelligenceString);

            if (selectedName.equals("")) {
                createError("Nome vazio!");
                return;
            }

            if (selectedRace.equals("")) {
                createError("Raça não selecionada!");
                return;
            }

            if (selectedClass.equals("")) {
                createError("Classe não selecionada!");
                return;
            }

            if (selectedForce < 0 || selectedForce > 10) {
                createError("Número de força inválido!");
                return;
            }

            if (selectedDexterity < 0 || selectedDexterity > 10) {
                createError("Número de destreza inválido!");
                return;
            }

            if (selectedConstitution < 0 || selectedConstitution > 10) {
                createError("Número de constituição inválido!");
                return;
            }

            if (selectedIntelligence < 0 || selectedIntelligence > 10) {
                createError("Número de inteligência inválido!");
                return;
            }

            FichaDAO fichaDAO = new FichaDAO(getApplicationContext());

            if (fichaAtual != null) {
                fichaAtual.setNome(selectedName);
                fichaAtual.setRaca(selectedRace);
                fichaAtual.setClasse(selectedClass);
                fichaAtual.setForca(selectedForce);
                fichaAtual.setDestreza(selectedDexterity);
                fichaAtual.setConstituicao(selectedConstitution);
                fichaAtual.setInteligencia(selectedIntelligence);

                if (fichaDAO.update(fichaAtual)) {
                    Toast.makeText(getApplicationContext(), "Ficha atualizada!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao atualizar...", Toast.LENGTH_SHORT).show();
                }
            } else {
                Ficha ficha = new Ficha(
                    selectedName,
                    selectedRace,
                    selectedClass,
                    selectedForce,
                    selectedDexterity,
                    selectedConstitution,
                    selectedIntelligence
                );

                if (fichaDAO.create(ficha)) {
                    Toast.makeText(getApplicationContext(), "Ficha criada!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao criar...", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            createError("Algum erro ocorreu. Revise seus dados!");
        }
    }

}