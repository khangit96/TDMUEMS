package com.example.khang.binhduongemergency;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapter.HinhAnhAdapter;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import model.SuKienPhanAnh;
import model.URLData;
import model.URLDownload;


public class PhanAnhActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    FloatingActionButton fabTakePhoto, fabTakeVideo, fabTakeRecord, fabCall;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Binh Duong Hackathon";
    private Uri fileUri;
    public static List<URLData> urlDataList;
    HinhAnhAdapter hinhAnhAdapter;
    RecyclerView recyclerView;
    public static String videoPath = "";
    VideoView videoView;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location currentLocation = null;
    static int COUNT = 0;
    ProgressDialog pg;
    EditText edMoTa;
    String loaiPhanAnh = "";
    Handler handler;
    Runnable runnable;
    String tinhTrang = "Unresloved";
    String title = "";
    String address = "";
    List<URLDownload> urlDownloadList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_anh);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getStringExtra("tenLoai") != null) {
            title = getIntent().getStringExtra("tenLoai");
        } else {
            title = "Emergency";
        }

        setTitle(title);
        address = "Duy Tan University, 03, Quang Trung, Hai Chau, Đa Nang, VietNam";

        Date date = new Date();
        final String thoiGianNgan = date.getDate() + "/" + String.valueOf(date.getMonth() + 1);

        fabTakePhoto = (FloatingActionButton) findViewById(R.id.fabTakePhoto);
        fabTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PhanAnhActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permision not grant", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(PhanAnhActivity.this, new String[]{android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                } else {
                    captureImage();
                }
            }
        });

        fabCall = (FloatingActionButton) findViewById(R.id.fabCall);
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "01695821150"));
                if (ActivityCompat.checkSelfPermission(PhanAnhActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });

        edMoTa = (EditText) findViewById(R.id.edMoTa);
        urlDataList = new ArrayList<>();
        pg = new ProgressDialog(PhanAnhActivity.this);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                if (COUNT > 0) {
                    if (COUNT == urlDataList.size()) {
                        COUNT = 0;
                        videoPath = "";
                        videoView.setVisibility(View.GONE);

                        String thoiGian = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
                                Locale.getDefault()).format(new Date());
                        SuKienPhanAnh suKienPhanAnh = new SuKienPhanAnh(thoiGian, thoiGianNgan, 108.212338, 16.060761, edMoTa.getText().toString(), loaiPhanAnh, urlDownloadList, address, tinhTrang);
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("SuKienPhanAnh").push();
                        mDatabase.setValue(suKienPhanAnh, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                urlDataList.clear();
                                urlDownloadList.clear();
                                hinhAnhAdapter.notifyDataSetChanged();
                                pg.dismiss();
                                Toast.makeText(getApplicationContext(), "Successfully processed", Toast.LENGTH_LONG).show();
                                Intent feedbackIntent = new Intent(PhanAnhActivity.this, FeedbackActivity.class);
                                feedbackIntent.putExtra("TITLE", title);
                                feedbackIntent.putExtra("KEY", databaseReference.getKey());
                                startActivity(feedbackIntent);
                            }
                        });
                        handler.removeCallbacks(this);

                    }
                }

            }
        };


        mGoogleApiClient = new GoogleApiClient.Builder(PhanAnhActivity.this)
                .addConnectionCallbacks(PhanAnhActivity.this)
                .addOnConnectionFailedListener(PhanAnhActivity.this)
                .addApi(LocationServices.API).build();


        fabTakeVideo = (FloatingActionButton) findViewById(R.id.fabTakeVideo);
        fabTakeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhanAnhActivity.this, TakeVideoActivity.class));
            }
        });

        fabTakeRecord = (FloatingActionButton) findViewById(R.id.fabTakeRecord);
        fabTakeRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                }
            }
        });

        videoView = (VideoView) findViewById(R.id.videoPreview);

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        recyclerView.setHasFixedSize(true);
        hinhAnhAdapter = new HinhAnhAdapter(this, (ArrayList<URLData>) urlDataList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(hinhAnhAdapter);

    }

    public void getResultFromApiAi() {
        /*api ai*/
        final AIConfiguration config = new AIConfiguration("891af0fc29904363b428f3d48c5a842e",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        final AIDataService aiDataService = new AIDataService(config);

        final AIRequest aiRequest = new AIRequest();
        aiRequest.setQuery(edMoTa.getText().toString());
        new AsyncTask<AIRequest, Void, AIResponse>() {
            @Override
            protected AIResponse doInBackground(AIRequest... requests) {
                final AIRequest request = requests[0];
                try {
                    final AIResponse response = aiDataService.request(aiRequest);
                    return response;
                } catch (AIServiceException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                if (aiResponse != null) {
                    final Result result = aiResponse.getResult();
                    //resultApiAi = result.getFulfillment().getSpeech();
                    loaiPhanAnh = result.getFulfillment().getSpeech();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://smarthome-5d11a.appspot.com");
                    int i;
                    for (i = 0; i < urlDataList.size(); i++) {
                        uploadImageToFirebase(storageRef, i);
                    }
                }
            }
        }.execute(aiRequest);

    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                findViewById(R.id.tvHinhAnh).setVisibility(View.VISIBLE);
                hinhAnhAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                edMoTa.setText(result.get(0).toString());
            }
        }
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        Log.d("test", "IMG");
        return mediaFile;
    }

    private void captureImage() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        urlDataList.add(new URLData(fileUri.getPath().toString(), "IMG"));
        Log.d("test", urlDataList.size() + "");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void uploadImageToFirebase(StorageReference storageRef, final int pos) {
        final URLData url = urlDataList.get(pos);
        Uri file = Uri.fromFile(new File(url.filePath));
        StorageReference riversRef = storageRef.child("data/" + file.getLastPathSegment());
        UploadTask uploadTask;
        uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                urlDownloadList.add(new URLDownload(taskSnapshot.getMetadata().getContentType(), taskSnapshot.getMetadata().getDownloadUrl().toString()));
                COUNT++;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_phan_anh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuUpload) {
//            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            if (!statusOfGPS) {
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            }
            // else {
            // if (currentLocation != null) {
            runOnUiThread(runnable);
            pg.setMessage("Processing data...");
            pg.setCanceledOnTouchOutside(false);
            pg.show();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            getResultFromApiAi();
//            //}
//
//            //   }


        } else if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            videoPath = "";
            return true;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        if (!videoPath.equals("")) {
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(videoPath);
            videoView.start();
            findViewById(R.id.tvVideo).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mGoogleApiClient.disconnect();
        videoPath = "";
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (currentLocation == null) {
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    public String getAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no data";

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        Toast.makeText(getApplicationContext(), "Connected:" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

}
