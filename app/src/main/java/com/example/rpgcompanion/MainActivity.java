package com.example.rpgcompanion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.rpgcompanion.dao.FichaDAO;
import com.example.rpgcompanion.fragmentos.DadoDialogFragment;
import com.example.rpgcompanion.fragmentos.FichaDetalheFragment;
import com.example.rpgcompanion.fragmentos.FichaListaFragment;
import com.example.rpgcompanion.fragmentos.InfoDialogFragment;
import com.example.rpgcompanion.model.Ficha;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity implements FichaListaFragment.AoClicarNaFicha,
        InfoDialogFragment.AoClicarEmInfo {

    private FichaListaFragment fichaListaFragment;
    private FragmentManager mFragmentManager;
    private Button btnCriarFicha;

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        fichaListaFragment = (FichaListaFragment) mFragmentManager.findFragmentById(R.id.fragmentoLista);

        btnCriarFicha = findViewById(R.id.btnCriarFicha);
        btnCriarFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CriacaoFichaActivity.class);
                startActivity(intent);
            }
        });

        createNotificationChannel();

        Log.e(TAG, "onCreate");

        LongOperation lo = new LongOperation(this);
        lo.execute("Curiosidade 1", "Curiosidade 2", "Curiosidade 3", "Curiosidade 4 ", "Curiosidade 5");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.acao_pesquisar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Pesquisar...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fichaListaFragment.buscar(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dados_item:
                Intent it = new Intent(this,DadosActivity.class);
                startActivity(it);
                break;
            case R.id.anotacoes_item:
                Intent ait = new Intent(this,AnotacoesAventuraActivity.class);
                startActivity(ait);
                break;
            case R.id.acao_info:
                InfoDialogFragment infoDialogFragment = new InfoDialogFragment();
                infoDialogFragment.show(mFragmentManager,"INFO");
                break;
            case R.id.bestiario:
                Intent it2 = new Intent(this,BestiarioActivity.class);
                startActivity(it2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clicouNaFicha(Ficha ficha) {
        Intent intent = new Intent(MainActivity.this, CriacaoFichaActivity.class);
        intent.putExtra("ficha", ficha);

        startActivity(intent);
    }

    @Override
    public void pressionouFicha(Ficha ficha) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Confirmar exclusão:");
        builder.setMessage("Deseja excluir a ficha: " + ficha.getNome() + "?");
        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FichaDAO fichaDAO = new FichaDAO(getApplicationContext());

                if (fichaDAO.delete(ficha)) {
                    Toast.makeText(getApplicationContext(), "Ficha removida!", Toast.LENGTH_SHORT).show();
                    carregarFichas();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao remover ficha...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Não", null);

        builder.create();
        builder.show();
    }

    @Override
    public void aoClicar(int botao) {
        if(botao == DialogInterface.BUTTON_POSITIVE) {
            Intent it = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/JoaoVSouto/rpg-companion-app"));
            startActivity(it);
        }
    }

    public void carregarFichas() {
        FichaDAO fichaDAO = new FichaDAO(getApplicationContext());
        fichaListaFragment.setarFichas(fichaDAO.list());
    }

    @Override
    protected void onStart() {
        this.carregarFichas();
        super.onStart();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private class LongOperation extends AsyncTask<String, String, String> {

        private static final String TAG = "longoperation";
        private Context ctx;
        private AtomicInteger notificationId = new AtomicInteger(0);

        LongOperation(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            for (String s : params) {
                Log.e(TAG, s);

                publishProgress(s);

                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep((long) 36e+6); //(long) 36e+6 para notificações a cada 1h, 2000 para ver notificacoes mais rapido
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }
            return "Executed";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            for (String title: values) {
                sendNotification(title, notificationId.incrementAndGet());
            }
        }

        void sendNotification(String title, int notificationId) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx,CHANNEL_ID)
                    .setContentTitle(String.format("%s ", title))
                    .setContentText("TUDO O QUE VOCÊ PRECISA SABER SOBRE RPG")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_launcher_foreground);

            Intent actionClickNotification = new Intent(ctx,MainActivity.class);

            PendingIntent acaoPendente = PendingIntent.getActivity(
                    ctx,
                    0,
                    actionClickNotification,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            builder.setContentIntent(acaoPendente);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
            notificationManager.notify(notificationId, builder.build());
        }
    }
}