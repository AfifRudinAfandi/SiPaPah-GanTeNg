package com.sipapah.simpel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sipapah.simpel.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OptionMenuActivity extends AppCompatActivity {

    TextView namapengguna, nikpengguna;
    View btn_back;
    LinearLayout btn_exit, edit_profil, about,btn_riwayat;


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
    Dialog dialog;
    TextView btn_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);

        btn_riwayat = findViewById(R.id.btn_riwayat);
        btn_back = findViewById(R.id.btn_back);
        btn_exit = findViewById(R.id.btn_exit);
        edit_profil = findViewById(R.id.editProfil);
        namapengguna = findViewById(R.id.nama_pengguna);
        nikpengguna = findViewById(R.id.nik);
        about = findViewById(R.id.btn_about);
        dialog = new Dialog(this);



        sharedpreferences = getSharedPreferences(SplashScreen.my_shared_preferences2, Context. MODE_PRIVATE);
        nomorhp = sharedpreferences.getString(TAG_NOMORHP, null);

        String tipe = "ambil";
        onResume();
        ViewProfile(nomorhp, tipe);




        btn_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(OptionMenuActivity.this, LaporanActivity.class);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        edit_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                Intent intent = new Intent(OptionMenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlertAbout();
            }
        });
    }

    public void ShowAlertAbout(){
        dialog.setContentView(R.layout.alert_about);
        btn_close = dialog.findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Konfirmasi");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah Anda yakin akan log out?")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent intent = new Intent(OptionMenuActivity.this, RegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
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
                        if (nama.isEmpty()) {
                            namapengguna.setText("Lengkapi Profil Anda");
                        } else {
                            namapengguna.setText(nama);
                        }
                        String nik =jObj.getString("nik");
                        if (nik.isEmpty()) {
                            nikpengguna.setText("");
                        } else {
                            nikpengguna.setText("NIK. "+nik);
                        }

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
