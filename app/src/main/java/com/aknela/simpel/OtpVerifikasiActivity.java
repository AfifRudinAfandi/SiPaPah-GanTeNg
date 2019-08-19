package com.aknela.simpel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aknela.simpel.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OtpVerifikasiActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    OtpView otpView;
    LinearLayout ubah;
    TextView nomortlp;
    public int counter;
    String nomor;
    SharedPreferences sharedpreferences;
    SharedPreferences sharedpreferences2;
    public static final String my_shared_preferences = "MyPrefs" ;
    public static final String my_shared_preferences2 = "MyPrefs2" ;
    public static final String TAG_NOMOR = "nomor";
    public final static String TAG_NOMORHP = "nomorhp";

    int success;
    private String url = Server.URL + "pengguna.php";
    private static final String TAG = OtpVerifikasiActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifikasi);


        mAuth = FirebaseAuth.getInstance();
        ubah = findViewById(R.id.ubah);
        otpView = findViewById(R.id.otp_view);
        nomortlp = findViewById(R.id.nomortlp);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        sharedpreferences2 = getSharedPreferences(my_shared_preferences2, Context.MODE_PRIVATE);


        sharedpreferences = getSharedPreferences(RegisterActivity.my_shared_preferences, Context. MODE_PRIVATE);
        nomor = sharedpreferences.getString(TAG_NOMOR, null);
        nomortlp.setText("62"+nomor);




        final TextView send = (TextView) findViewById(R.id.send);
        send.setVisibility(View.GONE);


        final TextView counttime = findViewById(R.id.counttime);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                counttime.setText("00 : " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                counttime.setVisibility(View.INVISIBLE);
                send.setVisibility(View.VISIBLE);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        while (true) {
                            String phonenumber = getIntent().getStringExtra("phonenumber");
                            sendVerificationCode(phonenumber);
                            send.setVisibility(View.INVISIBLE);
                            counttime.setVisibility(View.VISIBLE);
                            new CountDownTimer(60000, 1000) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    counttime.setText("00 : " + millisUntilFinished / 1000);
                                }

                                @Override
                                public void onFinish() {
                                    counttime.setVisibility(View.INVISIBLE);
                                }
                            }.start();
                            return;

                        }
                    }
                });
            }
        }.start();



        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override public void onOtpCompleted(String otp) {

                String code = otpView.getText().toString().trim();
                verifyCode(code);

            }
        });


        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpVerifikasiActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);


    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            String nomorhp = nomortlp.getText().toString().trim();
                            String tipe = "daftar" ;
                            tambahPengguna(nomorhp,tipe);
                            Log.i("test", nomorhp);
                            // menyimpan login ke session
                            SharedPreferences.Editor editor = sharedpreferences2.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_NOMORHP, nomorhp);
                            editor.commit();
                            Intent intent = new Intent(OtpVerifikasiActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra(TAG_NOMORHP, nomorhp);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(OtpVerifikasiActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                });
    }


    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
//                TaskExecutors.MAIN_THREAD,
                this,
                mCallBack
        );
        Log.i("info_otp",number);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            verificationId = s;
            super.onCodeSent(s, forceResendingToken);


        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpView.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpVerifikasiActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void tambahPengguna(final String nomorhp, final String tipe) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Check for error node in json
                    if (success == 1) {
                        String nomorhp = jObj.getString(TAG_NOMORHP);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nomorhp", nomorhp);
                params.put("tipe", tipe);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }





}