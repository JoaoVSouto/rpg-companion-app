package com.example.rpgcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BestiarioInfoActivity extends AppCompatActivity {

    TextView infoName;
    TextView infoSize;
    TextView infoType;
    TextView infoHit;
    TextView infoArmor;
    TextView infoConstitution;
    TextView infoIntelligence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestiario_info);

        infoName = findViewById(R.id.infoName);
        infoSize = findViewById(R.id.infoSize);
        infoType = findViewById(R.id.infoType);
        infoHit = findViewById(R.id.infoHit);
        infoArmor = findViewById(R.id.infoArmor);
        infoConstitution = findViewById(R.id.infoConstitution);
        infoIntelligence = findViewById(R.id.infoIntelligence);

        Intent it = getIntent();
        String besta = it.getExtras().getString("besta");

        MinhaTarefa tarefa = new MinhaTarefa();
        String urlApi = "https://www.dnd5eapi.co/api/monsters/"+besta;
        tarefa.execute(urlApi);
    }

    class MinhaTarefa extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;

            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {
                //1. acessar a API/Web service
                //1.1 passar o objeto do tipo URL
                URL url = new URL(stringUrl);

                //1.2 abre conexao, conectar do serviço e recupera resposta (dados)
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                //2. recuperar os dados
                inputStream = conexao.getInputStream();

                //2.1 bytes -> caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //2.2 fazer leitura dos caracteres

                BufferedReader reader = new BufferedReader(inputStreamReader);

                buffer = new StringBuffer();
                //2.3 usar o reader para ler as informações e armazenar no buffer

                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);


            //3. tratando dados json
            String name = null;
            String size = null;
            String type = null;
            String hit = null;
            String armor = null;
            String constitution = null;
            String intelligence = null;

            try {
                JSONObject jsonObject = new JSONObject(resultado);

                name = jsonObject.getString("name");
                size = jsonObject.getString("size");
                type = jsonObject.getString("type");
                hit = jsonObject.getString("hit_points");
                armor = jsonObject.getString("armor_class");
                constitution = jsonObject.getString("constitution");
                intelligence = jsonObject.getString("intelligence");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            infoName.setText(name);
            infoSize.setText(size);
            infoType.setText(type);
            infoHit.setText(hit);
            infoArmor.setText(armor);
            infoConstitution.setText(constitution);
            infoIntelligence.setText(intelligence);
        }
    }

}
