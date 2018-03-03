package com.example.khang.executorapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyService extends Service {
    public static String moTa = "";
    public static String diaChi = "";
    static String viTri = "";
    public static double viDo = 3;
    public static double kinhDo = 3;

    @Override
    public void onStart(Intent intent, int startId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("NhanVien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                moTa = dataSnapshot.child("moTa").getValue().toString();
                viTri = dataSnapshot.child("viTri").getValue().toString();
                viDo = (double) dataSnapshot.child("viDo").getValue();
                kinhDo = (double) dataSnapshot.child("kinhDo").getValue();
                diaChi = dataSnapshot.child("diaChi").getValue().toString();
                Log.d("test", "viDo:" + viDo + ",KinhDo: " + kinhDo);
                sendNoti();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sendNoti() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.mipmap.binhduongems);
        mBuilder.setSubText("Thông báo khẩn cấp");
        mBuilder.setContentTitle(moTa);
        PendingIntent dongYPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getApplicationContext(), 3, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.binhduongems, "Đồng ý", pIntent);
        mBuilder.addAction(R.drawable.binhduongems, "Hủy", dismissIntent);
        mBuilder.setSound(alarmSound);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentText(viTri);

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(3, mBuilder.build());
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}