package com.example.rpgcompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BestiarioActivity extends AppCompatActivity {
    ListView itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestiario);
        itens = findViewById(R.id.listaBestas);

        MinhaTarefa tarefa = new MinhaTarefa();
        String urlApi = "https://www.dnd5eapi.co/api/monsters/";
        tarefa.execute(urlApi);
        itens.setClickable(true);
        itens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = itens.getItemAtPosition(position);
                String nameBesta = o.toString();
                nameBesta = nameBesta.replace(" ", "-").toLowerCase();
                Intent it = new Intent(getApplicationContext(),BestiarioInfoActivity.class);
                it.putExtra("besta",nameBesta);
                startActivity(it);
            }
        });
    }

    public void setListBestas(List<String> bestas){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bestas);
        itens.setAdapter(adapter);
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

            JSONArray results = null;
            List<String> bestas = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(resultado);
                results = jsonObject.getJSONArray("results");
                for (int i=0; i < results.length(); i++) {
                    JSONObject f = results.getJSONObject(i);
                    bestas.add(f.getString("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            setListBestas(bestas);
        }
    }

}