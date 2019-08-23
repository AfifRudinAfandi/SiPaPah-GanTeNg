package com.sipapah.simpel.helpers;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public class MyValidation {

    private Context context;

    public MyValidation(Context context) {
        this.context = context;
    }

    public boolean isEmptyField(String yourField){
        return !TextUtils.isEmpty(yourField);
    }

    public boolean isValidateEmail(String email){
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isMatch(String password, String retypePassword){
        return password.equals(retypePassword);
    }

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
