package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.khang.binhduongemergency.R;

import java.util.ArrayList;

import model.URLData;

public class HinhAnhAdapter extends RecyclerView.Adapter<HinhAnhAdapter.MyViewHolder> {
    Context context;
    ArrayList<URLData> urlDataList;


    public HinhAnhAdapter(Context context, ArrayList<URLData> urlDataList) {
        this.urlDataList = urlDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_hinh_anh, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(urlDataList.get(position).filePath)
                .into(holder.imgHinhAnh);
    }


    @Override
    public int getItemCount() {
        return urlDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhAnh;

        public MyViewHolder(View v) {
            super(v);
            imgHinhAnh = (ImageView) v.findViewById(R.id.img);
        }
    }
} 