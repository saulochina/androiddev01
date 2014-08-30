package br.com.cursoandroid.cadalunos.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.cursoandroid.cadalunos.Helper.DatabaseHelper;
import br.com.cursoandroid.cadalunos.bean.Aluno;

/**
 * Created by rafael.felicio on 29/08/2014.
 */
public class AlunoDao {

    private static final String TABLE = "Aluno";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public AlunoDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public Long create(String nome, String email) {
        Long result = null;
        try {
            ContentValues cv = new ContentValues();
            cv.put("nome", nome);
            cv.put("email", email);
            database = dbHelper.getWritableDatabase();
            result = database.insert(TABLE, null, cv);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Aluno> getAlunos() {
        List<Aluno> lista = new ArrayList<Aluno>();
        database = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM Aluno;";

        Cursor c = (Cursor) database.rawQuery(query, null);

        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do{
                    Aluno aluno = new Aluno();

                    aluno.setId(c.getLong(c.getColumnIndex("_id")));
                    aluno.setNome(c.getString(c.getColumnIndex("nome")));
                    aluno.setEmail(c.getString(c.getColumnIndex("email")));
                    lista.add(aluno);
                }while(c.moveToNext());
            }
        }

        return lista;
    }


}
