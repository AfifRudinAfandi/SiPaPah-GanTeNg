package com.aknela.simpel;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aknela.simpel.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    View btn_back;
    EditText et_nama, et_nik, et_alamat;
    TextView btn_simpan, et_nomortlp;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        btn_back = findViewById(R.id.btn_back);
        et_nama = findViewById(R.id.nama);
        et_nik = findViewById(R.id.nik);
        et_alamat = findViewById(R.id.alamat);
        et_nomortlp = findViewById(R.id.nomortlp);
        btn_simpan = findViewById(R.id.btn_simpan);


        sharedpreferences = getSharedPreferences(SplashScreen.my_shared_preferences2, Context.MODE_PRIVATE);
        nomorhp = sharedpreferences.getString(TAG_NOMORHP, null);
        et_nomortlp.setText(nomorhp);


        String tipe = "ambil";
        ViewProfile(nomorhp, tipe);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString();
                String nik = et_nik.getText().toString();
                String alamat = et_alamat.getText().toString();
                String nomor = et_nomortlp.getText().toString();
                String tipe = "ubah";
                if (nama.isEmpty() || alamat.isEmpty()) {
                    BottomSheetDialogBelumLengkap bottomSheet2 = new BottomSheetDialogBelumLengkap();
                    bottomSheet2.show(getSupportFragmentManager(), "exampleBottomSheet");
                } else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        addprofile(nama, nik, alamat, nomor, tipe);

                        BottomSheetDialogSimpan bottomSheet = new BottomSheetDialogSimpan();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    } else {
                        Snackbar.make(v, "Tidak ada koneksi internet", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });


        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void addprofile(final String nama, final String nik, final String alamat, final String nomor, final String tipe) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {


                        Log.e("Successfully!", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", nama);
                Log.i("nama", nama);
                params.put("nik", nik);
                params.put("alamat", alamat);
                params.put("nomor", nomor);
                params.put("tipe", tipe);

                return params;
            }

        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    private void ViewProfile(final String nomor, final String tipe) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);


                    // Check for error node in json
                    if (success == 1) {

                        String nama = jObj.getString("nama");
                        et_nama.setText(nama);

                        String nik = jObj.getString("nik");
                        et_nik.setText(nik);

                        String alamat = jObj.getString("alamat");
                        et_alamat.setText(alamat);

                        Log.e("Successfully!", jObj.toString());


                    } else {


                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


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

}
