package com.aknela.simpel;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    public static final String my_shared_preferences = "MyPrefs" ;
    public final static String TAG_NOMOR = "nomor";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText = findViewById(R.id.editTextPhone);
        button = findViewById(R.id.buttonContinue);


        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomor  = editText.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(TAG_NOMOR, nomor);
                editor.commit();

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+" + "62" + number;

                Intent intent = new Intent(RegisterActivity.this, OtpVerifikasiActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                Log.i("num", phoneNumber);
                startActivity(intent);
                finish();

            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Intent intent = new Intent(this, HomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }
}
