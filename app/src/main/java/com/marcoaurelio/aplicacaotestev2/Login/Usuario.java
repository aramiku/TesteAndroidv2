package com.marcoaurelio.aplicacaotestev2.Login;

import android.util.Log;

import com.google.gson.JsonObject;
import com.marcoaurelio.aplicacaotestev2.Login.Routes;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Usuario {

    private Routes routes;
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    public String postBody;
    private MainPresenter mp;

    public Usuario(MainPresenter mp){
        this.mp = mp;
    }

    public void loginNetwork(String email, String senha) {
        routes = new Routes();
        try {
            postRequest(email,senha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void postRequest(String email, String senha) throws IOException {

        postBody="{\n" +
                "    \"user\": \"" + email + "\",\n" +
                "    \"password\": \"" + senha + "\"\n" +
                "}";

        OkHttpClient client = new OkHttpClient();

        JsonObject postData = new JsonObject();
        postData.addProperty("user", email);
        postData.addProperty("password", senha);

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody postBody = RequestBody.create(JSON, postData.toString());
        Request post = new Request.Builder()
                .url(routes.urlLogin)
                .post(postBody)
                .build();

        client.newCall(post).enqueue(new Callback() {
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

                    //Log.i("data", responseBody.string());

                    JSONObject json = new JSONObject(responseBody.string());
                    Log.d("login",json.getJSONObject("userAccount").getString("userId")+ " "+json.getJSONObject("userAccount").getString("name"));

                    Class cl = routes.routeDaRespostaLogin(json);
                    mp.passarDadosPessoais(json.getJSONObject("userAccount").getString("userId"),json.getJSONObject("userAccount").getString("name"),json.getJSONObject("userAccount").getString("bankAccount"),json.getJSONObject("userAccount").getString("agency"),json.getJSONObject("userAccount").getString("balance"));
                    mp.rotaATomar(cl);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

}

