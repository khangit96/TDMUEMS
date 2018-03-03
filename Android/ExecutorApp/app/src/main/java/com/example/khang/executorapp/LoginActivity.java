package com.example.khang.executorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btLogin;
    EditText edUsername;
    EditText edPassword;
    List<String> coQuanList;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btLogin = (Button) findViewById(R.id.btLogin);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);

        coQuanList = new ArrayList<>();
        coQuanList.add("Traffic");
        coQuanList.add("Emergency");
        coQuanList.add("Security");
        coQuanList.add("Fire Fighting");
        coQuanList.add("Environment");

        Spinner dropdown = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,coQuanList);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("LoaiPhanAnhChinh/"+pos+"/coQuan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            CoQuan coQuan = dt.getValue(CoQuan.class);

                            String username=edUsername.getText().toString();
                            String password=edPassword.getText().toString();

                            if(coQuan.username.equals(username)&&coQuan.password.equals(password)){
                                coQuan.key=dt.getKey().toString();
                                Intent intent=new Intent(LoginActivity.this,Main2Activity.class);
                                Bundle bd=new Bundle();
                                bd.putInt("POS",pos);
                                bd.putSerializable("COQUAN",coQuan);
                                intent.putExtra("BD_COQUAN",bd);
                                startActivity(intent);
                                return;
                            }
                        }
                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
