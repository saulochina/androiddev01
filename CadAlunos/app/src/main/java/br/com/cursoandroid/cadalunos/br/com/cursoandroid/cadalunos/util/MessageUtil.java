package br.com.cursoandroid.cadalunos.br.com.cursoandroid.cadalunos.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ralb on 05/09/14.
 */
public class MessageUtil {

    private Context mContext;

    public MessageUtil(Context context){
        this.mContext = context;
    }

    public void showMessage(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
