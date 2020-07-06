package com.marcoaurelio.aplicacaotestev2.ListaMovimentacao;

import android.util.Log;

import com.google.gson.JsonObject;
import com.marcoaurelio.aplicacaotestev2.Login.Routes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MovimentacoesModel {
    private ListaMovimentacoesActivity listMov;

    public MovimentacoesModel(ListaMovimentacoesActivity listMov){
        this.listMov = listMov;
    }

    Routes routes;

    /*public void  puxaMov2(String userId){

        routes = new Routes();

        OkHttpClient client = new OkHttpClient();

        Request get = new Request.Builder()
                .url(routes.urlLancamentosDoUsuario + userId)
                .build();

        client.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                    ListaMovimentacoesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                    //Log.d("login", responseBody.string());
                    JSONObject json = new JSONObject(responseBody.string());
                    JSONArray jsonArray = json.getJSONArray("statementList");

                    List<HashMap<String, String>> list =  retConteudoLV(jsonArray);
                    listMov.populaLista(list);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/


    public List<HashMap<String, String>> retConteudoLV(JSONArray json){
        List<HashMap<String , String>> fillmapret = new ArrayList<HashMap<String, String>>();

        if(json.length()>0){
            int index = 1;
            while(index < json.length()){
                JSONObject jobj = null;
                try {
                    jobj = json.getJSONObject(index);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> map = new HashMap<String, String>();
                try {
                    map.put("rowid",jobj.getString("title"));
                    map.put("col_1",jobj.getString("desc"));
                    map.put("col_2",jobj.getString("value"));
                    map.put("col_3",jobj.getString("date") );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fillmapret.add(map);
                index++;
            }
        }else{
            fillmapret = null;
        }
        return fillmapret;

   }
}
