package com.example.rpgcompanion.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rpgcompanion.model.Ficha;
import com.example.rpgcompanion.utils.FichaDBHelper;

import java.util.ArrayList;
import java.util.List;

public class FichaDAO {
    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public FichaDAO(Context context) {
        FichaDBHelper dbHelper = new FichaDBHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    public boolean create(Ficha ficha) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", ficha.getNome());
        contentValues.put("raca", ficha.getRaca());
        contentValues.put("classe", ficha.getClasse());
        contentValues.put("forca", ficha.getForca());
        contentValues.put("destreza", ficha.getDestreza());
        contentValues.put("constituicao", ficha.getConstituicao());
        contentValues.put("inteligencia", ficha.getInteligencia());

        try {
            write.insert(FichaDBHelper.TABELA_FICHAS, null, contentValues);
            Log.i("Info", "Registro salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Info", "Erro ao salvar... " + e.getMessage());
            return false;
        }

        return true;
    }

    public List<Ficha> list() {
        List<Ficha> fichas = new ArrayList<>();

        String sql = "SELECT * FROM " + FichaDBHelper.TABELA_FICHAS + ";";

        Cursor c = read.rawQuery(sql, null);

        c.moveToFirst();

        while (c.moveToNext()) {
            Ficha ficha = new Ficha();

            Long id = c.getLong(c.getColumnIndexOrThrow("id"));
            String nome = c.getString(c.getColumnIndexOrThrow("nome"));
            String raca = c.getString(c.getColumnIndexOrThrow("raca"));
            String classe = c.getString(c.getColumnIndexOrThrow("classe"));
            int forca = c.getInt(c.getColumnIndexOrThrow("forca"));
            int destreza = c.getInt(c.getColumnIndexOrThrow("destreza"));
            int constituicao = c.getInt(c.getColumnIndexOrThrow("constituicao"));
            int inteligencia = c.getInt(c.getColumnIndexOrThrow("inteligencia"));

            ficha.setId(id);
            ficha.setNome(nome);
            ficha.setRaca(raca);
            ficha.setClasse(classe);
            ficha.setForca(forca);
            ficha.setDestreza(destreza);
            ficha.setConstituicao(constituicao);
            ficha.setInteligencia(inteligencia);

            fichas.add(ficha);
        }

        c.close();

        return fichas;
    }

    public boolean update(Ficha ficha) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", ficha.getNome());
        contentValues.put("raca", ficha.getRaca());
        contentValues.put("classe", ficha.getClasse());
        contentValues.put("forca", ficha.getForca());
        contentValues.put("destreza", ficha.getDestreza());
        contentValues.put("constituicao", ficha.getConstituicao());
        contentValues.put("inteligencia", ficha.getInteligencia());

        try {
            String[] args = {ficha.getId().toString()};
            write.update(FichaDBHelper.TABELA_FICHAS, contentValues, "id=?", args);
            Log.i("Info", "Registro atualizado com sucesso!");
        } catch (Exception e) {
            Log.i("Info", "Erro ao atualizar registro... " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean delete(Ficha ficha) {
        try {
            String[] args = {ficha.getId().toString()};
            write.delete(FichaDBHelper.TABELA_FICHAS, "id=?", args);
            Log.i("Info", "Registro deletado com sucesso!");
        } catch (Exception e) {
            Log.i("Info", "Erro ao deletar registro... " + e.getMessage());
            return false;
        }

        return true;
    }
}
