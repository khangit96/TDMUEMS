package com.example.khang.executorapp;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.khang.executorapp.NotificationHelper.NEGATIVE_CLICK;
import static com.example.khang.executorapp.NotificationHelper.POSITIVE_CLICK;

public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback {
    List<DataSnapshot> dtList;
    private GoogleMap mMap;
    List<Marker> originMarkers = new ArrayList<>();
    List<Marker> destinationMarkers = new ArrayList<>();
    List<Polyline> polylinePaths = new ArrayList<>();
    LatLng duyTanUni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bd = getIntent().getBundleExtra("BD_COQUAN");
        final CoQuan coQuan = (CoQuan) bd.getSerializable("COQUAN");
        setTitle(coQuan.ten);
        dtList = new ArrayList<>();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("LoaiPhanAnhChinh/" + bd.getInt("POS") + "/coQuan/" + coQuan.key + "/Emergency").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dtList.clear();
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    dtList.add(dt);
                    Log.d("test", dt.getValue().toString());
                }
                //1 description
                //0 location emergency
                //6 keyEmergency
                //7 keyEvent
                //9 latitude emergency
                //10 longtitude emergency
                //8 longtitude agency
                //17 latitude agency
                if (dtList.size() > 0) {

                    double latitudeAgency = Double.parseDouble((dtList.get(17).getValue().toString()));
                    double longtitudeAgency = Double.parseDouble((dtList.get(8).getValue().toString()));
                    LatLng latLngAgency = new LatLng(latitudeAgency, longtitudeAgency);
                    String currentLocation = "" + duyTanUni.latitude + "," + duyTanUni.longitude;
                    String des = "" + latitudeAgency + "," + longtitudeAgency;
                    getDataFromGoogleMapServer(currentLocation, des);
                    mMap.addMarker(new MarkerOptions().position(duyTanUni).title("Emergency Location"));
                    mMap.addMarker(new MarkerOptions().position(latLngAgency).title(coQuan.ten).icon(BitmapDescriptorFactory.fromResource(R.drawable.congan)));
                    showHeadsUpNotification(dtList.get(1).getValue().toString(), dtList.get(7).getValue().toString(), dtList.get(6).getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showHeadsUpNotification(String content, String keyEvent, String keyEmergency) {
        int notificationId = new Random().nextInt();

        Intent intent = new Intent(this, Main2Activity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent acceptIntent = new Intent(this, NotificationReceiver.class);
        acceptIntent.putExtra("notiID", notificationId);
        acceptIntent.putExtra("KEY_EVENT", keyEvent);
        acceptIntent.putExtra("KEY_EMERGENCY", keyEmergency);
        acceptIntent.setAction(POSITIVE_CLICK);

        PendingIntent pIntent_positive = PendingIntent.getBroadcast(this, notificationId, acceptIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent refuseIntent = new Intent(this, NotificationReceiver.class);
        refuseIntent.putExtra("notiID", notificationId);
        refuseIntent.putExtra("KEY_EVENT", keyEvent);
        refuseIntent.putExtra("KEY_EMERGENCY", keyEmergency);
        refuseIntent.setAction(NEGATIVE_CLICK);

        PendingIntent pIntent_negative = PendingIntent.getBroadcast(this, notificationId, refuseIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder notification = NotificationHelper.createNotificationBuider(this,
                "Emergency Notification", content, R.drawable.ic_notifications, pIntent);

        notification.setPriority(Notification.PRIORITY_HIGH).setVibrate(new long[0]);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.setSound(alarmSound);
        notification.addAction(new NotificationCompat.Action(R.drawable.ic_notifications, "Accept", pIntent_positive));
        notification.addAction(new NotificationCompat.Action(R.drawable.ic_notifications, "Refuse", pIntent_negative));

        NotificationHelper.showNotification(this, notificationId, notification.build());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        duyTanUni = new LatLng(16.060761, 108.208175);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(duyTanUni, 15f));


        // getDataFromGoogleMapServer();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    /*
 * Parse JSon get direction from google map server
 * */
    public boolean getDataFromGoogleMapServer(String currentLocation, String des) {
        final Location destinationLocation = new Location("destinationLocation");
        destinationLocation.setLatitude(MyService.viDo);
        destinationLocation.setLongitude(MyService.kinhDo);

        final ProgressDialog progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setMessage("Getting path...");
        progressDialog.setCancelable(false);
        progressDialog.show();
//        String currentLocation = "11.053428,106.668529";
//        String des = "11.0517096,106.6679601";
        String SERVER_GOOGLEMAP = "https://maps.googleapis.com/maps/api/directions/json?origin=" + currentLocation + "&destination=" + des + "&key=AIzaSyD5Xzis5_DOGW2XLYvOQ7FCVvFzLsym9aA";

        final RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, SERVER_GOOGLEMAP, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonRoutes = jsonObject.getJSONArray("routes");
                            JSONObject jsonRoute = jsonRoutes.getJSONObject(0);
                            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                            //     JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                            String points = overview_polylineJson.getString("points");
                            drawPolyline(destinationLocation, decodePolyLine(points));
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });
        requestQueue.add(jsObjRequest);
        return true;
    }

    /*
    *
    * */

    public void drawPolyline(Location location, List<LatLng> points) {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }

        polylinePaths = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        LatLng endLocation = new LatLng(location.getLatitude(), location.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(endLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.congan)));
        destinationMarkers.add(marker);
        PolylineOptions polylineOptions = new PolylineOptions()
                .geodesic(true)
                .color(getResources().getColor(R.color.colorAccent))
                .width(15);

        for (int i = 0; i < points.size(); i++)
            polylineOptions.add(points.get(i));
        polylinePaths.add(mMap.addPolyline(polylineOptions));
//        getCurrentLocation(15f);
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                if (marker.getTitle() != null) {
//                    showBottonSheet(Integer.parseInt(marker.getTitle()));
//                }
//                previousMaker = marker;
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house_selected));
//
//                return true;
//            }
    }


    /*
    *
    * */
    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++

                ) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
