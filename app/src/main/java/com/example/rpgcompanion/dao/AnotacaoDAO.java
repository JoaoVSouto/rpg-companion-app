package com.example.rpgcompanion.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rpgcompanion.model.Anotacao;
import com.example.rpgcompanion.utils.AnotacoesDBHelper;

import java.util.ArrayList;
import java.util.List;

public class AnotacaoDAO {
    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public AnotacaoDAO(Context context) {
        AnotacoesDBHelper dbHelper = new AnotacoesDBHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    public boolean create(Anotacao anotacao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo", anotacao.getTitulo());
        contentValues.put("anotacoes", anotacao.getAnotacoes());

        try {
            write.insert(AnotacoesDBHelper.TABELA_ANOTACOES, null, contentValues);
            Log.i("Info", "Registro salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Info", "Erro ao salvar... " + e.getMessage());
            return false;
        }

        return true;
    }

    public List<Anotacao> list() {
        List<Anotacao> anotacoes = new ArrayList<>();

        String sql = "SELECT * FROM " + AnotacoesDBHelper.TABELA_ANOTACOES + ";";

        Cursor c = read.rawQuery(sql, null);

        c.moveToFirst();

        while (c.moveToNext()) {
            Anotacao anotacao = new Anotacao();

            anotacao.setId(c.getLong(c.getColumnIndexOrThrow("id")));
            anotacao.setTitulo(c.getString(c.getColumnIndexOrThrow("titulo")));
            anotacao.setAnotacoes(c.getString(c.getColumnIndexOrThrow("anotacoes")));

            anotacoes.add(anotacao);
        }

        c.close();

        return anotacoes;
    }

    public boolean update(Anotacao anotacao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo", anotacao.getTitulo());
        contentValues.put("anotacoes", anotacao.getAnotacoes());

        try {
            String[] args = {anotacao.getId().toString()};
            write.update(AnotacoesDBHelper.TABELA_ANOTACOES, contentValues, "id=?", args);
            Log.i("Info", "Registro atualizado com sucesso!");
        } catch (Exception e) {
            Log.i("Info", "Erro ao atualizar registro... " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean delete(Anotacao anotacao) {
        try {
            String[] args = {anotacao.getId().toString()};
            write.delete(AnotacoesDBHelper.TABELA_ANOTACOES, "id=?", args);
            Log.i("Info", "Registro deletado com sucesso!");
        } catch (Exception e) {
            Log.i("Info", "Erro ao deletar registro... " + e.getMessage());
            return false;
        }

        return true;
    }
}
