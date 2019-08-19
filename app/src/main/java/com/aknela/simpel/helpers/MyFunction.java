package com.aknela.simpel.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyFunction extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=MyFunction.this;

    }

    public void simpleIntent(Class dest){
        Intent move = new Intent(context, dest);
        startActivity(move);
    }
    public void simpleIntent(Class dest, int key, ArrayList<String> val){
        Intent move = new Intent(context, dest);
        for(int i=0; i<key; i++) {
            move.putExtra("KEY"+i, val.get(i));
        }
        startActivity(move);
    }
    public void simpleGetIntent(int key){
        ArrayList<String> val = new ArrayList<String>();

        Intent delivered = getIntent();
        for (int i=0; i<key; i++){
            val.add(delivered.getStringExtra("KEY"+i));
        }
    }
    public void simpleToast (String msg){
        Toast.makeText(context, msg , Toast.LENGTH_SHORT).show();
    }
    public void simpleToast (String msg, int time){
        if(time<2 && time>=1) {
            Toast.makeText(context, msg , Toast.LENGTH_SHORT).show();
        } else if(time>1) {
            Toast.makeText(context, msg , Toast.LENGTH_LONG).show();
        }
    }
    public void simpleAlert(String message){
        AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        builder.setTitle("informasi");

    }
}