package com.example.rpgcompanion.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class FichaDBHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String DB_NAME = "db_rpg_companion";
    public static String TABELA_FICHAS = "fichas";

    public FichaDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_FICHAS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome VARCHAR(50) NOT NULL," +
                "raca VARCHAR(20) NOT NULL," +
                "classe VARCHAR(20) NOT NULL," +
                "forca INTEGER NOT NULL," +
                "destreza INTEGER NOT NULL," +
                "constituicao INTEGER NOT NULL," +
                "inteligencia INTEGER NOT NULL);";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela...");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_FICHAS + ";";

        try {
            db.execSQL(sql);
            onCreate(db);
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao apagar tabela...");
        }
    }
}
