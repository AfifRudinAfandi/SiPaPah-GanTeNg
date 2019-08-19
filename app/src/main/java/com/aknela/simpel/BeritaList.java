package com.aknela.simpel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aknela.simpel.adapter.AdapterBerita;
import com.aknela.simpel.helpers.MyFunction;
import com.aknela.simpel.helpers.SharedPrefManager;
import com.aknela.simpel.models.BeritaItem;
import com.aknela.simpel.models.ResponseBerita;
import com.aknela.simpel.network.ApiServices;
import com.aknela.simpel.network.InitRetrofit;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaList extends MyFunction {



    private final static String TAG = BeritaList.class.getSimpleName();
    ConnectivityManager conMgr;
    RecyclerView rvListBerita;
    SwipeRefreshLayout swlayout;
    LinearLayout llLayout;
    View btn_back;

    private RecyclerView recyclerView;
    SharedPrefManager sharedPrefManager;
    Context mContext;
    View parentView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_list);


       rvListBerita = findViewById(R.id.rvListBerita);
       swlayout = findViewById(R.id.swlayout);
       llLayout = findViewById(R.id.llLayout);
       btn_back = findViewById(R.id.btn_back);


       btn_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
               finish();
           }
       });

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        parentView = llLayout;


        recyclerView = (RecyclerView) findViewById(R.id.rvListBerita);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {

            showBerita();

        } else {

            Toast.makeText(getApplicationContext(), "Periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
        }



        swlayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);


        swlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        showBerita();

                        swlayout.setRefreshing(false);

                    }
                }, 4000);
            }
        });
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//
//            new AlertDialog.Builder(this)
//                    .setTitle("Event Lamongan")
//                    .setMessage("Anda yakin ingin keluar?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            finish();
//                        }
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d("d", "onResume");
//        showBerita();
//        progressDialog = ProgressDialog.show(mContext, null, "Memuat...", true, false);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private void showBerita() {

        ApiServices api = InitRetrofit.getInstance();
        Call<ResponseBerita> beritaCall = api.requestBerita();

        beritaCall.enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().toString());
                    List<BeritaItem> data_berita = response.body().getBerita();
                    boolean status = response.body().getStatus();
                    if (status) {
                        AdapterBerita adapter = new AdapterBerita(BeritaList.this, data_berita);
                        recyclerView.setAdapter(adapter);
                    } else {
                        progressDialog.dismiss();
                    }
                }else{
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                Snackbar.make(parentView, "Koneksi Eror, periksa inernet anda.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       /* if (id == R.id.action_logout) {
            sharedPrefManager = new SharedPrefManager(mContext.getApplicationContext());
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_ALREADY_LOGINREADER, false);
            sharedPrefManager.saveSPString(SharedPrefManager.SP_IDUSER, "");
            sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, "");
            sharedPrefManager.saveSPString(SharedPrefManager.SP_FULLNAME, "");*//*
            Intent intent = new Intent(BeritaList.this, LoginActivity.class);
            startActivity(intent);
            Snackbar.make(parentView, "Keluar.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            finish();*/
//        }

        return super.onOptionsItemSelected(item);
    }
}
