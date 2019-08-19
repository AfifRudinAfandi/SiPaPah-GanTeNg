package com.aknela.simpel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.aknela.simpel.helpers.SharedPrefManager;

public class BeritaDetail extends AppCompatActivity {


    ImageView ivGambarBerita;
    CollapsingToolbarLayout toolbarLayout;
    AppBarLayout appBar;
    TextView judul;

    //    @BindView(R.id.tvTglTerbit)
    TextView tvTglTerbit;
//    @BindView(R.id.tvPenulis)
//    TextView tvPenulis;
    WebView wvKontenBerita;
    SharedPrefManager sharedPrefManager;
    Context mContext;

    String title, content, publisher, publishdate, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_detail);

        ivGambarBerita = findViewById(R.id.ivGambarBerita);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        appBar = findViewById(R.id.app_bar);
        judul = findViewById(R.id.judul);
        wvKontenBerita = findViewById(R.id.wvKontenBerita);
        tvTglTerbit = findViewById(R.id.tvTglTerbit);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);



        addFormData();


    }

    private void addFormData() {

        Intent terima = getIntent();

        title = terima.getStringExtra("judul");
        content = terima.getStringExtra("isi");
//        publisher = terima.getStringExtra("PUBLISHER");
        publishdate = terima.getStringExtra("waktu");
        foto = terima.getStringExtra("gambar");

        getSupportActionBar().setTitle("");
        judul.setText(title);

//        tvPenulis.setText("Oleh : " + publisher);
        tvTglTerbit.setText(publishdate+" | By Admin");
        Picasso.with(BeritaDetail.this).load(foto).into(ivGambarBerita);

        wvKontenBerita.getSettings().setJavaScriptEnabled(true);
        wvKontenBerita.setBackgroundColor(Color.TRANSPARENT);
        wvKontenBerita.loadData(content, "text/html; charset=utf-8", "UTF-8");
    }



}
