package com.example.khang.executorapp;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import static com.example.khang.executorapp.NotificationHelper.NEGATIVE_CLICK;
import static com.example.khang.executorapp.NotificationHelper.POSITIVE_CLICK;

public class Main2Activity extends AppCompatActivity {
RelativeLayout rl;
String content="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bd = getIntent().getBundleExtra("BD_COQUAN");
        CoQuan coQuan = (CoQuan) bd.getSerializable("COQUAN");
        setTitle(coQuan.ten);
        rl= (RelativeLayout) findViewById(R.id.rl);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("LoaiPhanAnhChinh/" + bd.getInt("POS") + "/coQuan/" + coQuan.key + "/Emergency").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(s!=null){
                    Log.d("test","demo");
                }

                Snackbar snackbar = Snackbar
                        .make(rl, "Emergency Notification", Snackbar.LENGTH_LONG)
                        .setDuration(Snackbar.LENGTH_INDEFINITE)
                        .setAction("Accept", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(rl, "Message is restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });


                // Changing message text color
                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);

                snackbar.show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(), "Changed", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.mipmap.binhduongems);
        mBuilder.setSubText("Emergency Notification");
        mBuilder.setContentTitle("Duy Tan University");
        Intent acceptPendingIntent = new Intent();
        acceptPendingIntent.setAction("ACCEPT");
        PendingIntent pIntentAccept = PendingIntent.getBroadcast(this, 1234, acceptPendingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(getApplicationContext(), 3, acceptPendingIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.addAction(R.drawable.binhduongems, "Accept", pIntentAccept);
        mBuilder.addAction(R.drawable.binhduongems, "Refuse", dismissIntent);
        mBuilder.setSound(alarmSound);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentText("dkdkdk");

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(3, mBuilder.build());
    }

    public void showHeadsUpNotification(String content) {
        int notificationId = new Random().nextInt();

        Intent intent = new Intent(this, Main2Activity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent positive = new Intent(this, NotificationReceiver.class);
        positive.putExtra("notiID", notificationId);
        positive.setAction(POSITIVE_CLICK);

        PendingIntent pIntent_positive = PendingIntent.getBroadcast(this, notificationId, positive, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent negative = new Intent(this, NotificationReceiver.class);
        negative.putExtra("notiID", notificationId);
        negative.setAction(NEGATIVE_CLICK);

        PendingIntent pIntent_negative = PendingIntent.getBroadcast(this, notificationId, negative, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder notification = NotificationHelper.createNotificationBuider(this,
                "Emergency Notification", content, R.drawable.ic_notifications, pIntent);

        notification.setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.setSound(alarmSound);
        notification.addAction(new NotificationCompat.Action(R.drawable.ic_notifications, "Postive", pIntent_positive));
        notification.addAction(new NotificationCompat.Action(R.drawable.ic_notifications, "Negative", pIntent_negative));

        NotificationHelper.showNotification(this, notificationId, notification.build());
    }
}
