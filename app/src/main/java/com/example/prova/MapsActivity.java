package com.example.prova;


import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public Double lat, log;
    public String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(getIntent().getExtras() != null){
            lat = Double.parseDouble(getIntent().getExtras().getString("Lat"));
            System.out.println("lat intent: " + getIntent().getExtras().getString("Lat"));
            log = Double.parseDouble(getIntent().getExtras().getString("Lon"));
            System.out.println("nome intent: " + getIntent().getExtras().getString("Nome"));
            nome = getIntent().getExtras().getString("Nome");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mMap != null){
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent Intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (Intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(Intent, CAMERA);
                    }
                    startActivity(Intent);
                    return true;
                }
            });

        }



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ponto = new LatLng(lat, log);
        mMap.addMarker(new MarkerOptions().position(ponto).title(getNome()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ponto));
        onResume();


    }
}