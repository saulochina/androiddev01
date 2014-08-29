package br.com.cursoandroid.cadalunos.Helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE Aluno (");
        query.append(" _id      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        query.append(" nome     TEXT NOT NULL,");
        query.append(" email    TEXT NOT NULL");
        query.append(");");
        try {
            db.execSQL(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
