package com.aknela.simpel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aknela.simpel.BeritaDetail;
import com.squareup.picasso.Picasso;
import com.aknela.simpel.R;
import com.aknela.simpel.config.MyConstant;
import com.aknela.simpel.models.BeritaItem;

import java.util.List;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.MyViewHolder> {

    Context context;
    List<BeritaItem> berita;

    public AdapterBerita(Context context, List<BeritaItem> data_berita) {
        this.context = context;
        this.berita = data_berita;
    }

    @Override
    public AdapterBerita.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.berita_item, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterBerita.MyViewHolder holder, final int position) {
        holder.tvJudul.setText(berita.get(position).getTitle());
        holder.tvTglTerbit.setText(berita.get(position).getPublishDate());
//        holder.tvPenulis.setText("Oleh : " + berita.get(position).getPublisher());

        Picasso.with(context).load(MyConstant.FOTO_URL + berita.get(position).getFoto()).into(holder.ivGambarBerita);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent varIntent = new Intent(context, BeritaDetail.class);
                varIntent.putExtra("judul", berita.get(position).getTitle());
                varIntent.putExtra("waktu", berita.get(position).getPublishDate());
//                varIntent.putExtra("PUBLISHER", berita.get(position).getPublisher());
                varIntent.putExtra("gambar", MyConstant.FOTO_URL+berita.get(position).getFoto());
                varIntent.putExtra("isi", berita.get(position).getContent());

                context.startActivity(varIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return berita.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGambarBerita;
        TextView tvJudul, tvTglTerbit, tvPenulis;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGambarBerita = (ImageView) itemView.findViewById(R.id.ivPosterBerita);
            tvJudul = (TextView) itemView.findViewById(R.id.tvJudulBerita);
            tvTglTerbit = (TextView) itemView.findViewById(R.id.tvTglTerbit);
//            tvPenulis = (TextView) itemView.findViewById(R.id.tvPenulis);
        }
    }
}
