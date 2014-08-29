package br.com.cursoandroid.cadalunos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.cursoandroid.cadalunos.Dao.AlunoDao;
import br.com.cursoandroid.cadalunos.bean.Aluno;


public class ListActivity extends Activity {
    ListView lvAlunos;
    String[] alunos;
    List<Aluno> lstAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getAlunos();
        lvAlunos = (ListView) findViewById(R.id.listaAlunos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos);
        lvAlunos.setAdapter(adapter);
        lvAlunos.setOnItemClickListener(clickListItem);
    }

    private void getAlunos() {
        AlunoDao dao = new AlunoDao(ListActivity.this);
        lstAlunos = dao.getAlunos();
        if (lstAlunos != null && lstAlunos.size() > 0) {
            alunos = new String[lstAlunos.size()];
            for (int i = 0; i < lstAlunos.size(); i++) {
                alunos[i] = lstAlunos.get(i).getNome();
            }
        }
    }

    private ListView.OnItemClickListener clickListItem = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ListActivity.this);
            dialog.setTitle(lstAlunos.get(position).getNome().toString().toUpperCase());
            dialog.setMessage(lstAlunos.get(position).getEmail().toString());
            dialog.setNegativeButton("Fechar",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = dialog.create();
            alert.show();
        }
    };

}
