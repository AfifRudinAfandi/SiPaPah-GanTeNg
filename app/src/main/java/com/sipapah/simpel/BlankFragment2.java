package com.sipapah.simpel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sipapah.simpel.app.AppController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlankFragment2 extends Fragment {

    private String url = Server.URL + "laporan.php";
    private static final String TAG = BlankFragment.class.getSimpleName();

    private List<RiwayatLapor> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    String nomorhp;
    String tipe = "riwayat2";
    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    public static final String TAG_NOMORHP = "nomorhp";

    public BlankFragment2() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        sharedpreferences = getActivity().getSharedPreferences(SplashScreen.my_shared_preferences2, Context.MODE_PRIVATE);
//        nomorhp = sharedpreferences.getString(TAG_NOMORHP, null);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
//
//        ViewLaporan2(nomorhp,tipe);
//        adapter = new MyAdapter(getActivity(), list);
//        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//
//        return rootView;
//    }
//
//    private void ViewLaporan2(final String nomor, final String tipe){
//
//        RiwayatLapor riwayatLapor = new RiwayatLapor();
//
//        riwayatLapor.setJenis("12");
//        riwayatLapor.setKeterangan("22");
//        riwayatLapor.setTanggal("32");
//        riwayatLapor.setStatus("42");
//        list.add(riwayatLapor);
//
//        riwayatLapor.setJenis("22");
//        riwayatLapor.setKeterangan("22");
//        riwayatLapor.setTanggal("32");
//        riwayatLapor.setStatus("42");
//        list.add(riwayatLapor);
//
//        Log.i("respon", "respon 2");
//    }
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedpreferences = getActivity().getSharedPreferences(SplashScreen.my_shared_preferences2, Context.MODE_PRIVATE);
    nomorhp = sharedpreferences.getString(TAG_NOMORHP, null);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);

        ViewLaporan2(nomorhp,tipe);
        return rootView;
    }

    private void ViewLaporan2(final String nomor, final String tipe){
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Response: " + response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        RiwayatLapor riwayatLapor = new RiwayatLapor();
                        JSONObject jsonObjectJenis = jsonArray.getJSONObject(j);
                        riwayatLapor.setJenis(jsonObjectJenis.getString("jenis"));
                        riwayatLapor.setKeterangan(jsonObjectJenis.getString("keterangan"));
                        riwayatLapor.setTanggal(jsonObjectJenis.getString("tanggal"));
                        riwayatLapor.setStatus(jsonObjectJenis.getString("ket_status"));
                        riwayatLapor.setLokasi(jsonObjectJenis.getString("lokasi"));
                        list.add(riwayatLapor);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new MyAdapter(getActivity(), list);
                recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);

                Log.i("list", list.toString());

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
