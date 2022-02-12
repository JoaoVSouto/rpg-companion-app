package com.example.rpgcompanion.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class AnotacoesDBHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String DB_NAME = "db_rpg_companion2";
    public static String TABELA_ANOTACOES = "anotacoes";

    public AnotacoesDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_ANOTACOES +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "titulo VARCHAR(30) NOT NULL," +
                "anotacoes TEXT NOT NULL);";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela!");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela...");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_ANOTACOES + ";";

        try {
            db.execSQL(sql);
            onCreate(db);
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao apagar tabela...");
        }
    }
}
