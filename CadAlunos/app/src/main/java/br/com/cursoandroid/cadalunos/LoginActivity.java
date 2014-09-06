package br.com.cursoandroid.cadalunos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.cursoandroid.cadalunos.Dao.UsuarioDao;
import br.com.cursoandroid.cadalunos.bean.Usuario;
import br.com.cursoandroid.cadalunos.br.com.cursoandroid.cadalunos.util.MessageUtil;


public class LoginActivity extends Activity {

    private EditText etLogin;
    private EditText etSenha;
    private Button btnLogin;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //instanciando os componentes de tela
        etLogin = (EditText) findViewById(R.id.login_etLogin);
        etSenha = (EditText) findViewById(R.id.login_etSenha);
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        btnSair = (Button) findViewById(R.id.login_btnSair);

        //listener dos botoes
        btnLogin.setOnClickListener(clickLogin);
        btnSair.setOnClickListener(clickSair);
    }

    protected View.OnClickListener clickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String usuario = etLogin.getText().toString();
            String senha = etSenha.getText().toString();
            validaCampos();
            validarUsuario(usuario, senha);
        }
    };

    protected View.OnClickListener clickSair = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    /**
     * Método para validar o usuario que foi digitado no login
     */
    private void validarUsuario(String usuario, String senha) {
        try {
            //instanciar DAO para consulta no BD
            UsuarioDao dao = new UsuarioDao(LoginActivity.this);

            //instanciar Objeto Usuario para container de dados.
            Usuario usu = new Usuario();

            //DAO retorna um objeto Usuario se verdadeiro.
            usu = dao.getUsuario(usuario, senha);

            //se o usuario existir passamos as informações de uma activity para outra
            if (usu != null) {

                //Bundle - container onde podemos passar parametros e objetos entre activities
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usu);

                //Intent - representa a nossa "Intenção", ou seja, ORIGEM - DESTINO e Parametros
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtras(bundle);

                //Arranque para transação entre activities
                startActivity(intent);

                //apresentar uma mensagem de interação com o usuario a respeito da funcionalidade de login
                new MessageUtil(LoginActivity.this).showMessage("Seja bem vindo!");

                finish();

            } else {
                //apresentar mensagem de erro caso o login não tenha sido efetuado.
                new MessageUtil(LoginActivity.this).showMessage("LOGIN E SENHA INVALIDOS");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("LOGIN", e.getMessage());
        }
    }

    /**
     * Método para validar os campos obrigatorios
     */
    private void validaCampos() {
        if (etLogin.getText().toString().equalsIgnoreCase("")) {
            etLogin.setError("Login obrigatório");
            etLogin.requestFocus();
            return;
        }

        if (etSenha.getText().toString().equalsIgnoreCase("")) {
            etSenha.setError("Senha obrigatório");
            etSenha.requestFocus();
            return;
        }
    }
}
