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
import br.com.cursoandroid.cadalunos.br.com.cursoandroid.cadalunos.util.MessageUtil;


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
        try {
            lstAlunos = dao.getAlunos();
            if (lstAlunos != null && lstAlunos.size() > 0) {
                alunos = new String[lstAlunos.size()];
                for (int i = 0; i < lstAlunos.size(); i++) {
                    alunos[i] = lstAlunos.get(i).getNome();
                }
            }else{
                alunos = new String[]{};
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private ListView.OnItemClickListener clickListItem = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
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
            //TODO: Implementar funcionalidade para apagar registro.
            dialog.setPositiveButton("Apagar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    AlunoDao dao = new AlunoDao(ListActivity.this);
                    if(dao.delete(lstAlunos.get(position).getId())){
                        new MessageUtil(ListActivity.this).showMessage("Registro apagado com sucesso.");
                        dialog.dismiss();
                        onCreate(null);
                    }else{
                        new MessageUtil(ListActivity.this).showMessage("ERRO ao tentar apagar registro.");
                        dialog.dismiss();
                    }
                }
            });
            AlertDialog alert = dialog.create();
            alert.show();
        }
    };

}
