package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.khang.binhduongemergency.PhanAnhActivity;
import com.example.khang.binhduongemergency.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.PhanAnhAdapter;
import model.LoaiPhanAnhChinh;


public class PhanAnhFragment extends Fragment {

    GridView gridPhanAnh;
    PhanAnhAdapter phanAnhAdapter;
    ArrayList<LoaiPhanAnhChinh> danhSachLoaiPhanAnhChinh;
    View v;
    ProgressDialog pg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_phan_anh, container, false);
        initGridLoaiPhanAnh();
        pg = new ProgressDialog(getActivity());
        pg.setMessage("Loading...");
        pg.show();
        pg.setCanceledOnTouchOutside(false);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("LoaiPhanAnhChinh");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    LoaiPhanAnhChinh loaiPhanAnhChinh = dt.getValue(LoaiPhanAnhChinh.class);
                    danhSachLoaiPhanAnhChinh.add(loaiPhanAnhChinh);
                    phanAnhAdapter.notifyDataSetChanged();
                }
                pg.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

    public void initGridLoaiPhanAnh() {
        gridPhanAnh = (GridView) v.findViewById(R.id.gridPhanAnh);
        danhSachLoaiPhanAnhChinh = new ArrayList<>();
        phanAnhAdapter = new PhanAnhAdapter(getContext(), R.layout.list_phan_anh, danhSachLoaiPhanAnhChinh);
        gridPhanAnh.setAdapter(phanAnhAdapter);

        gridPhanAnh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(danhSachLoaiPhanAnhChinh.get(i).ten.equals("Khác")){

                }
                else{
                    Intent intent = new Intent(getActivity(), PhanAnhActivity.class);
                    intent.putExtra("tenLoai", danhSachLoaiPhanAnhChinh.get(i).ten);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
