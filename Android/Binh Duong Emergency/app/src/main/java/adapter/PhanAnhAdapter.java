package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khang.binhduongemergency.R;

import java.util.ArrayList;

import model.LoaiPhanAnhChinh;

public class PhanAnhAdapter extends ArrayAdapter<LoaiPhanAnhChinh> {
    Context context;
    int layoutResourceId;
    ArrayList<LoaiPhanAnhChinh> danhSachLoaiPhanAnhChinh;


    public PhanAnhAdapter(Context context, int layoutResourceId, ArrayList<LoaiPhanAnhChinh> danhSachLoaiPhanAnhChinh) {
        super(context, layoutResourceId, danhSachLoaiPhanAnhChinh);
        this.context = context;
        this.danhSachLoaiPhanAnhChinh = danhSachLoaiPhanAnhChinh;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new RecordHolder();

        holder.tvTen = (TextView) row.findViewById(R.id.tvTen);
        holder.imgHinhAnh = (ImageView) row.findViewById(R.id.imgHinhAnh);
        holder.tvTen.setText(danhSachLoaiPhanAnhChinh.get(position).ten);

        Glide.with(context)
                .load(danhSachLoaiPhanAnhChinh.get(position).hinhAnh)
                .into(holder.imgHinhAnh);

        row.setTag(holder);

        return row;

    }


    /*
    *
    * */
    static class RecordHolder {
        TextView tvTen;
        ImageView imgHinhAnh;
    }
} 