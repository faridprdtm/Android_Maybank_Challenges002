package com.farid.challenges002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView list_view;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list_view=findViewById(R.id.list_view);
        list_view.setOnItemClickListener(this);

        getJSON();
    }

    private void getJSON(){

        //bantuan dari class AsyncTask
        class  GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,
                        "Mengambil data", "Harap Menunggu...",
                        false, false);
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(10000);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                HttpHandler handler= new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL);
                return result;
            }
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING= message;
                Log.d("DATA_JSON:",JSON_STRING);
                //menampilkan data dalam bentuk list view
                displayAllData();
            }
        }
        GetJSON getJSON =new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA JSON", JSON_STRING);


            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id = object.getString(Konfigurasi.TAG_JSON_ID);
                String nama = object.getString(Konfigurasi.TAG_JSON_NAMA);
                String umur = object.getString(Konfigurasi.TAG_JSON_UMUR);

                HashMap<String, String> nasabah = new HashMap<>();
                nasabah.put(Konfigurasi.TAG_JSON_ID, id);
                nasabah.put(Konfigurasi.TAG_JSON_NAMA, nama);
                nasabah.put(Konfigurasi.TAG_JSON_UMUR, umur);
                //
                list.add(nasabah);
            }
        }catch (Exception ex){
                ex.printStackTrace();
        }
        //adapter
        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list,
                R.layout.activity_list_item,
                new String[]{Konfigurasi.TAG_JSON_ID, Konfigurasi.TAG_JSON_NAMA, Konfigurasi.TAG_JSON_UMUR},
                new int[]{R.id.txt_id, R.id.txt_nama, R.id.txt_umur}
        );
        list_view.setAdapter(adapter);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(MainActivity.this, LihatDetailDataActivity.class);
        HashMap<String,String>map=(HashMap)parent.getItemAtPosition(position);
        String nasabahId= map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.NASABAH_ID, nasabahId);
        startActivity(myIntent);
    }

}