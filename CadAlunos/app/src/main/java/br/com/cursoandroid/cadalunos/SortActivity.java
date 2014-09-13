package br.com.cursoandroid.cadalunos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.cursoandroid.cadalunos.Dao.AlunoDao;
import br.com.cursoandroid.cadalunos.bean.Aluno;
import br.com.cursoandroid.cadalunos.br.com.cursoandroid.cadalunos.util.MessageUtil;


public class SortActivity extends Activity {

    private Button btnSort;
    private TextView tvNome;
    private List<Aluno> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        btnSort = (Button) findViewById(R.id.btnSort);
        tvNome = (TextView) findViewById(R.id.tvNomeSorteio);
        btnSort.setOnClickListener(clickSort);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlunoDao dao = new AlunoDao(SortActivity.this);
        lista = new ArrayList<Aluno>();
        lista = dao.getAlunos();
    }

    private View.OnClickListener clickSort = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startSort();
        }
    };

    private void startSort() {
        if (lista != null && lista.size() > 0) {
            if(lista.size() == 1){
                new MessageUtil(SortActivity.this).showMessage("Cadastre mais usuarios para realizar o sorteio.");
                return;
            }
            final ProgressDialog progress = ProgressDialog.show(SortActivity.this, "Aguarde", "SORTEANDO...");

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.dismiss();
                            Collections.shuffle(lista);
                            String nome = lista.get(0).getNome().toString();
                            tvNome.setText(nome.toUpperCase());
                        }
                    });
                }
            };

            thread.start();
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SortActivity.this);
            dialogBuilder.setTitle("Atenção!");
            dialogBuilder.setMessage("Não há alunos cadastrados,\n deseja efetuar o cadastro agora?");
            dialogBuilder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();
                }
            });
            dialogBuilder.setNegativeButton("NAO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            AlertDialog mDialog = dialogBuilder.create();
            mDialog.show();
        }
    }
}
