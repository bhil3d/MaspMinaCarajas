package com.example.maspminacarajas;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.maspminacarajas.databinding.ActivityMapsBinding;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    List<Address>listaGeoCoder;
    private static final int LOCATION_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (permissaodeLocalizacao()){

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try{listaGeoCoder = new Geocoder(this).getFromLocationName("Mina de Ferro Carajas", 1);
        }
        catch (Exception e){e.printStackTrace();
        }

        double lougitude = listaGeoCoder.get(0).getLongitude();
        double latitude = listaGeoCoder.get(0).getLatitude();


        }
        else {
            solicitarPermissaolocalisacao();
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
        LatLng sydney = new LatLng(-6.071400, -49.904732);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Parauapebas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        { mMap.setMyLocationEnabled(true); }


    }
private boolean permissaodeLocalizacao(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
        ){ return true; }

        else {
            return false;
        }
}

private void solicitarPermissaolocalisacao(){

    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);

}

}