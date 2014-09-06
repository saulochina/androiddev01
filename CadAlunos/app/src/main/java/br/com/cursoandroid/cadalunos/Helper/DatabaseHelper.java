package br.com.cursoandroid.cadalunos.Helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael.felicio on 29/08/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "CadAluno";
    private static final Integer DBVERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> listaQuery = new ArrayList<String>();

        StringBuilder query;

        query = new StringBuilder();
        query.append("CREATE TABLE Aluno (");
        query.append(" _id      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        query.append(" nome     TEXT NOT NULL,");
        query.append(" email    TEXT NOT NULL");
        query.append(");");

        listaQuery.add(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE Usuario (");
        query.append(" _id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        query.append(" usuario  TEXT NOT NULL,");
        query.append(" login  TEXT NOT NULL,");
        query.append(" senha  TEXT NOT NULL);");

        listaQuery.add(query.toString());

        query = new StringBuilder();
        query.append("INSERT INTO Usuario(usuario, login, senha)VALUES(");
        query.append("'administrador', ");
        query.append("'admin', ");
        query.append("'123456');");

        listaQuery.add(query.toString());

        try {
            for (String item : listaQuery)
                db.execSQL(item.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
