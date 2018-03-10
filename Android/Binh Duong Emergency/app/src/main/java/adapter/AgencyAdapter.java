package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khang.binhduongemergency.R;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import model.LoaiPhanAnhChinh;

/**
 * Created by khang on 10/03/2018.
 */

public class AgencyAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;

    ArrayList<String> agencyList;

    public AgencyAdapter(Context context, int layoutResourceId, ArrayList<String> agencyList) {
        super(context, layoutResourceId, agencyList);
        this.context = context;
        this.agencyList = agencyList;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);

        TextView tvAgencyName = (TextView) convertView.findViewById(R.id.tvAgencyName);
        TextView tvPos = (TextView) convertView.findViewById(R.id.tvPos);
        tvAgencyName.setText(agencyList.get(position));
        tvPos.setText(String.valueOf(position + 1));

        return convertView;

    }

}
