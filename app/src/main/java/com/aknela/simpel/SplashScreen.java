package com.aknela.simpel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedpreferences2;
    public static final String my_shared_preferences2 = "MyPrefs2" ;
    public final static String TAG_NOMORHP = "nomorhp";
    Boolean session = false;
    String nomorhp;
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        final int welcomeScreenDisplay = 2000; // 3000 = 3 detik
        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);

                } finally {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        sharedpreferences2 = getSharedPreferences(my_shared_preferences2, Context.MODE_PRIVATE);
                        session = sharedpreferences2.getBoolean(session_status, false);
                        nomorhp = sharedpreferences2.getString(TAG_NOMORHP, null);
                        Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra(TAG_NOMORHP, nomorhp);
                        startActivity(i);
                    }
                    else {
                        Intent intent = new Intent(SplashScreen.this, RegisterActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        };

        welcomeThread.start();


    }

}
