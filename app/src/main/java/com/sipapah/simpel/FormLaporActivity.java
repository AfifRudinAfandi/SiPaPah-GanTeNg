package com.sipapah.simpel;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sipapah.simpel.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class FormLaporActivity extends AppCompatActivity {


    private String jsonURL = "http://sipapahganteng.com/php/kat-sampah.php";
    private final int jsoncode = 1;
    private ArrayList<ModelData> modelDataArrayList;
    private ArrayList<String> names = new ArrayList<String>();
    private Spinner spinner;

    SharedPreferences sharedpreferences;
    public final static String TAG_NOMORHP = "nomorhp";
    private String url = Server.URL + "laporan.php";
    private static final String TAG = FormLaporActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    int success;
    ConnectivityManager conMgr;
    View btn_back;
    TextView btn_lapor, lt, a;
    ImageView imageView, imageView2, imageView3;
    private LinearLayout btnCameraPermission;
    private LinearLayout btnCameraPermission2;
    private LinearLayout btnCameraPermission3;
    private LinearLayout btnCameraPermission4;
    private int CAMERA_PERMISSION_CODE = 24;
    EditText edMaps, ket;
    AppLocationService appLocationService;
    Bitmap bitmap1, bitmap2, bitmap3;
    String imageData1 ,imageData2,imageData3;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lapor);


        edMaps = findViewById(R.id.edMaps);
        lt = findViewById(R.id.lt);
        appLocationService = new AppLocationService(FormLaporActivity.this);
        btn_back = findViewById(R.id.btn_back);
        btn_lapor = findViewById(R.id.btn_lapor);
        spinner = findViewById(R.id.spinner);
        ket = findViewById(R.id.ket);
        a = findViewById(R.id.a);

        loadJSON();
        sharedpreferences = getSharedPreferences(SplashScreen.my_shared_preferences2, Context.MODE_PRIVATE);
        final String nomor = sharedpreferences.getString(TAG_NOMORHP, null);


        btn_lapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipe = "tambahLaporan";
                String lokasi = edMaps.getText().toString();
                String jenis = spinner.getSelectedItem().toString();
                String keterangan = ket.getText().toString();
                String langlong = lt.getText().toString();
                String gambar = a.getText().toString();
                imageData1 = imageToString(bitmap1);
                if (bitmap2 != null){
                    imageData2 = imageToString(bitmap2);
                }else {
                    imageData2="";
                }
                if (bitmap3 !=null){
                    imageData3 = imageToString(bitmap3);
                }else {
                    imageData3 ="";
                }


                if (lokasi.isEmpty() || keterangan.isEmpty()) {
                    BottomSheetDialogBelumLengkap bottomSheet2 = new BottomSheetDialogBelumLengkap();
                    bottomSheet2.show(getSupportFragmentManager(), "exampleBottomSheet");
                } else {
                    if (gambar.isEmpty()) {
                        BottomSheetDialogFoto bottomSheet = new BottomSheetDialogFoto();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    } else {
                        if (conMgr.getActiveNetworkInfo() != null
                                && conMgr.getActiveNetworkInfo().isAvailable()
                                && conMgr.getActiveNetworkInfo().isConnected()) {
                            Log.i("cek", "TesBro");
                            Lapor(lokasi, nomor, jenis, keterangan, langlong, tipe, imageData1, imageData2, imageData3);


                            BottomSheetDialogSukses bottomSheet = new BottomSheetDialogSukses();
                            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");

                            dissmis();

                        } else {
                            Snackbar.make(v, "Periksa koneksi internet anda", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        final ImageButton ibMaps = (ImageButton) findViewById(R.id.ibMaps);
        ibMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (ActivityCompat.checkSelfPermission(FormLaporActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FormLaporActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FormLaporActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                } else {

                    Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
//                        double latitude = location.getLatitude();
//                        double longitude = location.getLongitude();
//                        LocationAddress locationAddress = new LocationAddress();
//                        locationAddress.getAddressFromLocation(latitude, longitude,
//                                getApplicationContext(), new GeocoderHandler());
//                        LocationAddress locationAddress2 = new LocationAddress();
//                        locationAddress2.getAddressFromLocation2(latitude, longitude,
//                                getApplicationContext(), new GeocoderHandler2());
                        try {
                            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
//                            intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                            Intent intent = intentBuilder.build(FormLaporActivity.this);
                            startActivityForResult(intent, PLACE_PICKER_REQUEST);

                        }
//                        catch (GooglePlayServicesRepairableException
//                                | GooglePlayServicesNotAvailableException e) { e.printStackTrace();
//                        }
                        catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showSettingsAlert();
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        /**Initializing button**/
        btnCameraPermission = findViewById(R.id.button1);
        btnCameraPermission2 = findViewById(R.id.button2);
        btnCameraPermission3 = findViewById(R.id.button3);
        btnCameraPermission4 = findViewById(R.id.button4);
        imageView = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);


        btnCameraPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First checking if the app is already having the permission


                if (canOpenCamera()) {
                    openCamera();

                    //Existing the method with return
                    return;
                }

                //If the app has not the permission then asking for the permission
                requestCameraPermission();
            }
        });


        btnCameraPermission2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canOpenCamera()) {
                    openCamera2();

                    //Existing the method with return
                    return;
                }

                //If the app has not the permission then asking for the permission
                requestCameraPermission();
            }
        });

        btnCameraPermission3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canOpenCamera()) {
                    openCamera3();

                    //Existing the method with return
                    return;
                }

                //If the app has not the permission then asking for the permission
                requestCameraPermission();
            }
        });

        btnCameraPermission4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Tidak boleh melebihi 3 foto", Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                FormLaporActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        FormLaporActivity.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            edMaps.setText(locationAddress);

        }
    }

//    private class GeocoderHandler2 extends Handler {
//        @Override
//        public void handleMessage(Message message) {
//            String locationAddress2;
//            switch (message.what) {
//                case 1:
//                    Bundle bundle = message.getData();
//                    locationAddress2 = bundle.getString("address");
//                    break;
//                default:
//                    locationAddress2 = null;
//            }
//            lt.setText(locationAddress2);
//
//        }
//    }

    //We are calling this method to check the permission status

    private boolean canOpenCamera() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }


    //Requesting permission
    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//            Toast.makeText(getApplicationContext(), "Permission Required to Open Camera", Toast.LENGTH_SHORT).show();
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == CAMERA_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openCamera();
                //Displaying a toast
//                Toast.makeText(this, "Permission granted now you can open camera", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
//                Toast.makeText(this, "Oops you just denied the Camera permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    private void openCamera2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    private void openCamera3() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 102);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



//            if (requestCode == PLACE_PICKER_REQUEST
//                    && resultCode == Activity.RESULT_OK) {
//
//                Place place = PlacePicker.getPlace(data, this);
//                String toasMsg = String.format("Place : %s", place.getAddress());
////                edMaps.setText(toasMsg);
//
//            }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,getApplicationContext(), new GeocoderHandler());

                String lg = latitude +","+ longitude;
                lt.setText(lg);
//                String toastMsg = String.format(
//                        "Place: %s \n" +
//                                "Alamat: %s \n" + "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
//                edMaps.setText(toastMsg);
            }
        }

        if (requestCode == 100 && resultCode == RESULT_OK) {
            bitmap1 = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap1);
            btnCameraPermission.setVisibility(View.INVISIBLE);
            btnCameraPermission2.setVisibility(View.VISIBLE);
            a.setText("1");
        }
        if (requestCode == 101 && resultCode == RESULT_OK) {
            bitmap2 = (Bitmap) data.getExtras().get("data");
            imageView2.setImageBitmap(bitmap2);
            btnCameraPermission2.setVisibility(View.INVISIBLE);
            btnCameraPermission3.setVisibility(View.VISIBLE);
        }
        if (requestCode == 102 && resultCode == RESULT_OK) {
            bitmap3 = (Bitmap) data.getExtras().get("data");
            imageView3.setImageBitmap(bitmap3);
            btnCameraPermission3.setVisibility(View.INVISIBLE);
            btnCameraPermission4.setVisibility(View.VISIBLE);

        }
    }


    private void Lapor(final String lokasi, final String nomor, final String jenis, final String keterangan, final String langlong, final String tipe, final String imageData1, final String imageData2, final String imageData3) {

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
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Log.e(TAG, " Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                Log.i("ayolah", imageData1);
                params.put("imageData1", imageData1);
                params.put("imageData2", imageData2);
                params.put("imageData3", imageData3);
                Log.i("ayolah", imageData1);
                Log.i("ayolah2", imageData2);
                Log.i("ayolah3", imageData3);
                params.put("lokasi", lokasi);
                params.put("nomor", nomor);
                params.put("jenis", jenis);
                params.put("keterangan", keterangan);
                params.put("kode", langlong);
                params.put("tipe", tipe);
                return params;
            }
        };
//
//        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        strReq.setRetryPolicy(retryPolicy);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    private String imageToString(Bitmap bitmap) {
//        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodeImage;
        } catch (NullPointerException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }

    }

    public void onBackPress(){
        finish();
    }
    private void dissmis() {
        final long period = 1500;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                onBackPress();
            }
        }, 1500, period);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadJSON(){
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(jsonURL);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }

            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
    }

    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response.toString());
        switch (serviceCode) {
            case jsoncode:
                modelDataArrayList = parseInfo(response);
                // Application of the Array to the Spinner
                for (int i = 0; i < modelDataArrayList.size(); i++){
                    Log.d("coba", response.toString());
                    names.add(modelDataArrayList.get(i).getName().toString());
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, names);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner.setAdapter(spinnerArrayAdapter);
        }
    }

    public ArrayList<ModelData> parseInfo(String response) {
        ArrayList<ModelData> ModelArrayList = new ArrayList<>();
        try {
            JSONArray dataArray = new JSONArray(response);
            for (int i = 0; i < dataArray.length(); i++) {
                ModelData usrModel = new ModelData();
                JSONObject dataobj = dataArray.getJSONObject(i);
                usrModel.setName(dataobj.getString("jenis_sampah"));
                ModelArrayList.add(usrModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ModelArrayList;
    }


}