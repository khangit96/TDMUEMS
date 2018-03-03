package com.example.khang.binhduongemergency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fragment.Camera2VideoFragment;

public class TakeVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_video);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }
    }
}
