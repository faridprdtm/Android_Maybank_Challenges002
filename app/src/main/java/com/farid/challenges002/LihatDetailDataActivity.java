package com.farid.challenges002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class LihatDetailDataActivity extends AppCompatActivity {
    EditText edit_id, edit_nama, edit_umur, edit_jenis_kelamin, edit_pekerjaan;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Data Nasabah");

        edit_id = findViewById(R.id.edit_id);
        edit_nama = findViewById(R.id.edit_nama);
        edit_umur = findViewById(R.id.edit_umur);
        edit_jenis_kelamin = findViewById(R.id.edit_jenis_kelamin);
        edit_pekerjaan= findViewById(R.id.edit_pekerjaan);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.NASABAH_ID);
        edit_id.setText(id);

        // mengambil data JSON
        getJSON();
    }
    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailDataActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL, id);
                return result;

        }
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
}
    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id = object.getString(Konfigurasi.TAG_JSON_ID);
            String nama = object.getString(Konfigurasi.TAG_JSON_NAMA);
            String jenis_kelamin= object.getString(Konfigurasi.TAG_JSON_JENIS_KELAMIN);
            String umur= object.getString(Konfigurasi.TAG_JSON_UMUR);
            String pekerjaan= object.getString(Konfigurasi.TAG_JSON_PEKERJAAN);

            edit_id.setText("ID: " +id);
            edit_nama.setText("Nama: " +nama);
            edit_jenis_kelamin.setText("Gender: " +jenis_kelamin);
            edit_umur.setText("Umur: " +umur);
            edit_pekerjaan.setText("Pekerjaan: " +pekerjaan);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}