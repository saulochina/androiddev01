package br.com.cursoandroid.cadalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.cursoandroid.cadalunos.Dao.AlunoDao;
import br.com.cursoandroid.cadalunos.Helper.DatabaseHelper;


public class MainActivity extends Activity {

    private EditText etNome;
    private EditText etEmail;
    private Button btnSalvar;
    private Button btnListarAlunos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //criando database
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
        dbHelper.getWritableDatabase();
        dbHelper.close();

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(clickSalvar);
        btnListarAlunos = (Button)findViewById(R.id.btnListar);

        btnListarAlunos.setOnClickListener(clickListarAlunos);
    }

    private View.OnClickListener clickSalvar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            etNome.setError(null);
            etEmail.setError(null);

            try {
                if (etNome.getText().toString().isEmpty()) {
                    etNome.setError("Nome Obrigatório");
                    etNome.requestFocus();
                    return;
                }

                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Email Obrigatório");
                    etEmail.requestFocus();
                    return;
                }

                AlunoDao dao = new AlunoDao(MainActivity.this);

                if(dao.create(etNome.getText().toString(), etEmail.getText().toString()) > 0){
                    Toast.makeText(MainActivity.this,"Aluno cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    limparCampos();
                }else{
                    Toast.makeText(MainActivity.this,"ERRO ao cadastrar aluno.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private View.OnClickListener clickListarAlunos = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent it = new Intent(MainActivity.this, ListActivity.class);
            startActivity(it);
        }
    };

    private void limparCampos(){
        etNome.setText("");
        etEmail.setText("");
    }

}
