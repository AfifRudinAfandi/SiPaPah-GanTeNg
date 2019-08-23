package com.sipapah.simpel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sipapah.simpel.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    View btn_lapor, btn_option, btn_report;
    EditText nm,alm;

    int success;
    ConnectivityManager conMgr;
    private String url = Server.URL + "pengguna.php";
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String nomorhp;
    SharedPreferences sharedpreferences;
    public static final String TAG_NOMORHP = "nomorhp";
    String nama,alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_lapor = findViewById(R.id.btn_lapor);
        btn_option = findViewById(R.id.btn_option);
        btn_report = findViewById(R.id.btn_report);
        nm = findViewById(R.id.namaset);
        alm = findViewById(R.id.alamatset);

        sharedpreferences = getSharedPreferences(SplashScreen.my_shared_preferences2, Context. MODE_PRIVATE);
        nomorhp = sharedpreferences.getString(TAG_NOMORHP, null);


        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BeritaList.class);
                startActivity(intent);
            }
        });

        onResume();
        String tipe = "ambil";
        ViewProfile(nomorhp, tipe);







        btn_lapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onPause();
                onResume();
                nama = nm.getText().toString();
                alamat = alm.getText().toString();
                Log.i("so",nama);

                conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {


                    if (nama.isEmpty() || alamat.isEmpty()) {
                        BottomSheetDialogProfil bottomSheet = new BottomSheetDialogProfil();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    } else {

                        Log.i("cek", "TesBro");

                        Intent intent = new Intent(HomeActivity.this, FormLaporActivity.class);
                        startActivity(intent);

                    }
                } else {
                    onPause();
                    Snackbar.make(v, "Periksa koneksi internet anda", Snackbar.LENGTH_SHORT).show();
                }



            }
        });

        btn_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(HomeActivity.this, OptionMenuActivity.class);
                startActivity(intent);
            }
        });


    }


    private void ViewProfile(final String nomor, final String tipe) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response: "+ response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        String nama =jObj.getString("nama");
                        nm.setText(nama);
                        String alamat =jObj.getString("alamat");
                        alm.setText(alamat);
                        Log.e("Successfully!", jObj.toString());

                    } else {


                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, " Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nomor", nomor);
                params.put("tipe", tipe);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    @Override
    protected void onResume() {
        super.onResume();
        String tipe = "ambil";
        ViewProfile(nomorhp, tipe);
    }
}




//if (conMgr.getActiveNetworkInfo() != null
//        && conMgr.getActiveNetworkInfo().isAvailable()
//        && conMgr.getActiveNetworkInfo().isConnected()) {
//
//        } else {
//        }