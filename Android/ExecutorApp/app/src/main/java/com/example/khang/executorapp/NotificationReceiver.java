package com.example.khang.executorapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by khang on 07/03/2018.
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int noti_id = intent.getIntExtra("notiID", 0);
        String keyEvent = intent.getStringExtra("KEY_EVENT");
        String keyEmergency = intent.getStringExtra("KEY_EMERGENCY");
        String status = "";
        if (action.equals(NotificationHelper.POSITIVE_CLICK)) {

            //   Toast.makeText(context,"Positive click",Toast.LENGTH_LONG).show();
            Log.d("test", keyEvent + ":" + keyEmergency);
            status = "Processing";
            cancel(context, noti_id);

        } else if (action.equals(NotificationHelper.NEGATIVE_CLICK)) {
            //   Toast.makeText(context,"Negative click",Toast.LENGTH_LONG).show();
            Log.d("test", keyEvent + ":" + keyEmergency);
            status = "Refuse";
            cancel(context, noti_id);
        } else if (action.equals(NotificationHelper.REPLY_CLICK)) {
            // Log.d("Action Reply","Pressed"+getMessageText(intent));
            cancel(context, noti_id);
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("SuKienPhanAnh/" + keyEvent + "/Emergency/" + keyEmergency).child("status").setValue(status);
    }

    public static void cancel(Context context, int id) {
        NotificationManager notificacaoManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificacaoManager.cancel(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(NotificationHelper.REPLY_TEXT_KEY);
        }
        return null;
    }
}