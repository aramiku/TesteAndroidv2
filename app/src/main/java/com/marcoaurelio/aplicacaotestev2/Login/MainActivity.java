package com.marcoaurelio.aplicacaotestev2.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.marcoaurelio.aplicacaotestev2.R;


interface MainActivityMVP{
    void resultLogin(String title, String msg);
    void rotaATomar(Class cla);
    void gravarDadosPessoais(String userId, String name, String bankAccount, String agency, String balance);
}

public class MainActivity extends AppCompatActivity implements MainActivityMVP {

    public MainPresenterMVP presenter;
    private Button btnLogin;
    private EditText email;
    private EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        presenter = new MainPresenter(this);
        email = (EditText)findViewById(R.id.emailx);
        senha = (EditText)findViewById(R.id.senhax);
        btnLogin = (Button)findViewById(R.id.btnLoginx);
        btnLogin.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                loginToPresenter();
            }
        });
    }
    public void loginToPresenter(){
        MainActivity.this.presenter.login(email.getText().toString(),senha.getText().toString());
    }

    public void simpleAlert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        //listener.onDialogPositiveClick(NoticeDialogFragment.this);

                    }
                });
        builder.create()
                .show();
    }

    private MainActivity getActivity(){
        return this;
    }

    @Override
    public void resultLogin(String title, String msg) {
        simpleAlert(title,msg);
    }

    public void rotaATomar(Class cla){
        //Intent intsld = new Intent("com.geobraxsistemassjc.aplicacaosobcontrole.CadSaldoRndVarInv");

        startActivity(new Intent(getActivity(), cla));
    }

    public void gravarDadosPessoais(String userId, String name, String bankAccount, String agency, String balance){
        SharedPreferences prefs = ((MainActivity)getActivity()).getSharedPreferences("com.marcoaurelio.aplicacaotestev2", Context.MODE_PRIVATE);
        //String jsonst = prefs.getString("com.marcoaurelio.aplicacaotestev2.json", " ");
        prefs.edit().putString("com.marcoaurelio.aplicacaotestev2.userId", userId ).commit();
        prefs.edit().putString("com.marcoaurelio.aplicacaotestev2.name", name ).commit();
        prefs.edit().putString("com.marcoaurelio.aplicacaotestev2.bankAccount", bankAccount ).commit();
        prefs.edit().putString("com.marcoaurelio.aplicacaotestev2.agency", agency ).commit();
        prefs.edit().putString("com.marcoaurelio.aplicacaotestev2.balance", balance ).commit();
    }
}
