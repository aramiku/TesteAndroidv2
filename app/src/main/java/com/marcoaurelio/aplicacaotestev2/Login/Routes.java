package com.marcoaurelio.aplicacaotestev2.Login;

import android.content.Intent;

import com.marcoaurelio.aplicacaotestev2.ListaMovimentacao.ListaMovimentacoesActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Routes {

    public String urlLogin = "https://bank-app-test.herokuapp.com/api/login";

    public String urlLancamentosDoUsuario = "https://bank-app-test.herokuapp.com/api/statements/";

    public Class routeDaRespostaLogin(JSONObject json){
        JSONObject key = null;
        try {
            key = json.getJSONObject("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (key.length()==0){
            return ListaMovimentacoesActivity.class;
        }else{
            return MainActivity.class;
        }

    }

}
