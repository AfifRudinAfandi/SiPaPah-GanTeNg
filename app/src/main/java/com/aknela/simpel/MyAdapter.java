package com.aknela.simpel;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//    private String[] mDataset;
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        public CardView mCardView;
//        public TextView mTextView;
//        public TextView stts;
//        public TextView jenis;
//        public TextView tgl;
//
//        public MyViewHolder(View v) {
//            super(v);
//
//            mCardView = v.findViewById(R.id.card_view);
//            mTextView = v.findViewById(R.id.tv_text);
//            stts =  v.findViewById(R.id.stts);
//            jenis =  v.findViewById(R.id.jenis);
//            tgl =  v.findViewById(R.id.tgl);
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }
//
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view

//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.card_item, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        holder.mTextView.setText(mDataset[position]);
//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String currentValue = mDataset[position];
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    private Context mContext;
    private List<RiwayatLapor> mData;

    public MyAdapter(Context mContext, List<RiwayatLapor> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.card_item,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        myViewHolder.jenis.setText(mData.get(position).getJenis());
        myViewHolder.keterangan.setText(mData.get(position).getKeterangan());
        myViewHolder.tanggal.setText(mData.get(position).getTanggal());
        myViewHolder.status.setText(mData.get(position).getStatus());
        myViewHolder.lokasi.setText(mData.get(position).getLokasi());


//        if (){
//            matchPercentage.setBackgroundResource(R.color.match_blue);
//        }else if (match>=75 && match<85){
//            matchPercentage.setBackgroundResource(R.color.match_green);
//        }
        String jenis = myViewHolder.jenis.getText().toString();
        String status = myViewHolder.status.getText().toString();
//        String organik = "Sampah Organik";
//        String anorganik = "Sampah Anorganik";
//        if (jenis == organik) {
//            myViewHolder.jenis.setBackgroundResource(R.color.colorGrey);
//        }else if (jenis == anorganik) {
//            myViewHolder.jenis.setBackgroundResource(R.color.colorBlack);
//        }

        if(jenis.equals("Sampah Organik")){
            myViewHolder.jenis.setBackgroundResource(R.drawable.bg_rectangle_solid_green);
        }else if (jenis.equals("Sampah Anorganik")){
            myViewHolder.jenis.setBackgroundResource(R.drawable.bg_rectangle_solid_grey);
        }else if (jenis.equals("Sampah Bahan Berbahaya dan Beracun")){
            myViewHolder.jenis.setBackgroundResource(R.drawable.bg_rectangle_solid_red);
        }else if(jenis.equals("Sampah")){
            myViewHolder.jenis.setBackgroundResource(R.drawable.bg_rectangle_solid_green);
        }else if (jenis.equals("Gangguan Lingkungan")){
            myViewHolder.jenis.setBackgroundResource(R.drawable.bg_rectangle_solid_grey);
        }else if (jenis.equals("Layanan Jemputan Bank Sampah")){
            myViewHolder.jenis.setBackgroundResource(R.drawable.bg_rectangle_solid_red);
        }

        if(status.equals("Proses")){
            myViewHolder.status.setTextColor(Color.parseColor("#FFB52D"));
        }else if (status.equals("Selesai")){
            myViewHolder.status.setTextColor(Color.parseColor("#5BCAA8"));
        }else if (status.equals("Pending")){
            myViewHolder.status.setTextColor(Color.parseColor("#9DB2C5"));
        }

        myViewHolder.lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewHolder.lihat.isChecked()){
                    myViewHolder.lokasi.setVisibility(View.VISIBLE);
                }else {
                    myViewHolder.lokasi.setVisibility(View.GONE);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

//        CardView mcardview;
         TextView keterangan;
         TextView status;
         TextView jenis;
         TextView tanggal;
         TextView lokasi;
         ToggleButton lihat;



        public MyViewHolder(View itemView) {
        super(itemView);
//            mcardview = v.findViewById(R.id.card_view);
            keterangan = itemView.findViewById(R.id.tv_text);
            status =  itemView.findViewById(R.id.stts);
            jenis =  itemView.findViewById(R.id.jenis);
            tanggal =  itemView.findViewById(R.id.tgl);
            lokasi =  itemView.findViewById(R.id.lokasi);
            lihat =  itemView.findViewById(R.id.btn_lihat);
        }
    }
}




//    private String[] jenis;
//    private String[] keterangan;
//    private String[] tanggal;
//    private String[] status;
//    private Activity context;











//
//
//
//         ViewHolder(View v){
//             mcardview = v.findViewById(R.id.card_view);
//            keterangan = v.findViewById(R.id.tv_text);
//            status =  v.findViewById(R.id.stts);
//            jenis =  v.findViewById(R.id.jenis);
//            tanggal =  v.findViewById(R.id.tgl);
//         }
//    }



//}
