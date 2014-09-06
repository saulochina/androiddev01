package br.com.cursoandroid.cadalunos.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.cursoandroid.cadalunos.Helper.DatabaseHelper;
import br.com.cursoandroid.cadalunos.bean.Usuario;

/**
 * Created by ralb on 05/09/14.
 */
public class UsuarioDao {

    private static final String TABLE = "Usuario";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public UsuarioDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public Usuario getUsuario(String login, String senha) {
        Usuario usu = null;
        database = dbHelper.getReadableDatabase();
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT _id, usuario, login, senha FROM ");
            query.append(TABLE);
            query.append(" WHERE login = '");
            query.append(login);
            query.append("' AND senha = '");
            query.append(senha);
            query.append("';");

            Cursor c = (Cursor) database.rawQuery(query.toString(), null);

            if (c != null && c.getCount() > 0) {
                c.moveToFirst();

                usu = new Usuario();
                usu.setId(c.getInt(c.getColumnIndex("_id")));
                usu.setUsuario(c.getString(c.getColumnIndex("usuario")));
                usu.setLogin(c.getString(c.getColumnIndex("login")));
                usu.setSenha(c.getString(c.getColumnIndex("senha")));

            }
            c.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usu;
    }
}
