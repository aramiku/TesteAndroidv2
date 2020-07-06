package com.marcoaurelio.aplicacaotestev2.ListaMovimentacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.marcoaurelio.aplicacaotestev2.Login.MainActivity;
import com.marcoaurelio.aplicacaotestev2.Login.Routes;
import com.marcoaurelio.aplicacaotestev2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ListaMovimentacoesActivity extends AppCompatActivity {

    private ListView listaMovimentacao;
    List<HashMap<String, String>> fillMapsMain = new ArrayList<HashMap<String, String>>();
    SpecialAdapter adapter;
    private TextView tvname;
    private TextView tvconta;
    private TextView tvsaldo;
    private MovimentacoesModel movModel;
    private Routes routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listamovimentacao);
        movModel = new MovimentacoesModel(this);
        listaMovimentacao = (ListView)findViewById(R.id.listaMovimentacaox);
        tvname = (TextView)findViewById(R.id.namex);
        tvconta = (TextView)findViewById(R.id.contax);
        tvsaldo = (TextView)findViewById(R.id.saldox);
        routes = new Routes();
        colocaDadosPessoais();

    }

    private  void colocaDadosPessoais(){
        SharedPreferences prefs = ((ListaMovimentacoesActivity)getActivity()).getSharedPreferences("com.marcoaurelio.aplicacaotestev2", Context.MODE_PRIVATE);
        String userId = prefs.getString("com.marcoaurelio.aplicacaotestev2.userId", "NomeGetNG");
        String name = prefs.getString("com.marcoaurelio.aplicacaotestev2.name", "NomeGetNG");
        String bankAccount = prefs.getString("com.marcoaurelio.aplicacaotestev2.bankAccount", "NomeGetNG");
        String agency = prefs.getString("com.marcoaurelio.aplicacaotestev2.agency", "NomeGetNG");
        String balance = prefs.getString("com.marcoaurelio.aplicacaotestev2.balance", "NomeGetNG");

        tvname.setText(name);
        tvconta.setText(bankAccount);
        tvsaldo.setText(balance);

        //movModel.puxaMov2(userId);
        puxaMov2(userId);
    }
    public void populaLista(List<HashMap<String, String>> list){
        // Popula listview
        String[] from = new String[] {"rowid","col_1","col_2", "col_3"};
        int[] to = new int[] {R.id.item1, R.id.item2, R.id.item3, R.id.item4};
        fillMapsMain.clear();
        this.fillMapsMain = list;
        adapter = new SpecialAdapter(this.getApplicationContext(),fillMapsMain,R.layout.grid_item,from,to,2);
        listaMovimentacao.setAdapter(adapter);

        AdapterView.OnItemClickListener itemclick = new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intn = new Intent("com.geobraxsistemassjc.aplicacaosobcontrole.AlteraRndVarInvAplicacao");
                HashMap<String, String> map = fillMapsMain.get(i);
                long idrndvarinvaplic = Long.parseLong(map.get("col_1"));
                Log.d("huwuoeruworuowir", "id aplic =" + String.valueOf(idrndvarinvaplic));
                intn.putExtra("idrndvarinvaplic",idrndvarinvaplic);
                startActivity(intn);
                //Toast.makeText(CadVarAplicacoes.this.getApplicationContext(), "Edição será implementada .", Toast.LENGTH_LONG).show();
            }
        };
        listaMovimentacao.setOnItemClickListener(itemclick);
        // Fim Popula ListView
    }
    public void  puxaMov2(String userId){

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
                    final ResponseBody responseBody = response.body();
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
                                populaLista(list);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


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


    private ListaMovimentacoesActivity getActivity(){
        return this;
    }
}
