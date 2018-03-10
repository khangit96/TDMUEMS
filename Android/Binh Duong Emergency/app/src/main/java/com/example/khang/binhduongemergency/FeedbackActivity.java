package com.example.khang.binhduongemergency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;

import adapter.AgencyAdapter;

public class FeedbackActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvAgency;
    RippleBackground rippleBackground;
    List<String> agencyNameList;
    AgencyAdapter agencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvAgency = (ListView) findViewById(R.id.lvAgency);

        agencyNameList = new ArrayList<>();

        String title = getIntent().getStringExtra("TITLE");
        setTitle(title);

        rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        agencyNameList = new ArrayList<>();

        String key = getIntent().getStringExtra("KEY");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("SuKienPhanAnh/" + key + "/Emergency").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                agencyNameList.clear();

                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    if (dt.child("status").getValue().equals("Processing")) {
                        agencyNameList.add(dt.child("ten").getValue().toString());
                    }

                }

                if (agencyNameList.size() > 0) {
                    rippleBackground.stopRippleAnimation();
                    rippleBackground.setVisibility(View.GONE);

                    agencyAdapter = new AgencyAdapter(FeedbackActivity.this, R.layout.list_agency, (ArrayList<String>) agencyNameList);
                    lvAgency.setAdapter(agencyAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
