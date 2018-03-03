package com.example.khang.executorapp;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GoogleMap mMap;
    List<Marker> originMarkers = new ArrayList<>();
    List<Marker> destinationMarkers = new ArrayList<>();
    List<Polyline> polylinePaths = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        setTitle("Người thực thi");
//        startService(new Intent(MainActivity.this, MyService.class));
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //@Override
//    public void onMapReady(GoogleMap googleMap) {
////        mMap = googleMap;
////
////        LatLng tdmu = new LatLng(11.053428, 106.668529);
////        mMap.getUiSettings().setMyLocationButtonEnabled(true);
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            return;
////        }
////        mMap.setMyLocationEnabled(true);
////        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tdmu, 15f));
////        mMap.addMarker(new MarkerOptions().position(new LatLng(11.053428, 106.668529)).title("Vị trí phản ánh"));
////        mMap.addMarker(new MarkerOptions().position(new LatLng(11.0517096, 106.6679601)).title(MyService.diaChi).icon(BitmapDescriptorFactory.fromResource(R.drawable.congan)));
////
////        getDataFromGoogleMapServer();
////        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
////            @Override
////            public boolean onMarkerClick(Marker marker) {
////                return false;
////            }
////        });
//    }

    /*
   * Parse JSon get direction from google map server
   * */
//    public boolean getDataFromGoogleMapServer() {
//        String strDestinationLocation = String.valueOf(MyService.viDo) + "," + String.valueOf(MyService.kinhDo);
//        final Location destinationLocation = new Location("destinationLocation");
//        destinationLocation.setLatitude(MyService.viDo);
//        destinationLocation.setLongitude(MyService.kinhDo);
//
//        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("Đang lấy thông tin đường đi...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        String currentLocation = "11.053428,106.668529";
//        String des = "11.0517096,106.6679601";
//        String SERVER_GOOGLEMAP = "https://maps.googleapis.com/maps/api/directions/json?origin=" + currentLocation + "&destination=" + des + "&key=AIzaSyD5Xzis5_DOGW2XLYvOQ7FCVvFzLsym9aA";
//
//        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, SERVER_GOOGLEMAP, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        try {
//                            JSONArray jsonRoutes = jsonObject.getJSONArray("routes");
//                            JSONObject jsonRoute = jsonRoutes.getJSONObject(0);
//                            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
//                            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
//                            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
//                            //     JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
//                            String points = overview_polylineJson.getString("points");
//                            drawPolyline(destinationLocation, decodePolyLine(points));
//                            progressDialog.dismiss();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                    }
//                });
//        requestQueue.add(jsObjRequest);
//        return true;
//    }

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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.khang.executorapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.khang.executorapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
