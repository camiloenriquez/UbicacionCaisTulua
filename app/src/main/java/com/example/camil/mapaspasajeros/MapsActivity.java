package com.example.camil.mapaspasajeros;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.camil.mapaspasajeros.api.DatosApi;
import com.example.camil.mapaspasajeros.models.DatosColombia;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Retrofit retrofit;

    public final static  String TAG="DATOS_COLOMBIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        retrofit=new Retrofit.Builder().baseUrl("https://www.datos.gov.co/resource/").
                addConverterFactory(GsonConverterFactory.create()).build();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DatosApi service = retrofit.create(DatosApi.class);
        final Call<List<DatosColombia>> municipioCall = service.obtenerListaDatoss();

        municipioCall.enqueue(new Callback<List<DatosColombia>>() {
            @Override
            public void onResponse(Call<List<DatosColombia>> call, Response<List<DatosColombia>> response) {
                if (response.isSuccessful()) {
                    List listaMunicipios = response.body();
                    for (int i = 0; i < listaMunicipios.size(); i++) {
                        DatosColombia dC = (DatosColombia) listaMunicipios.get(i);
                       LatLng sydney = new LatLng(dC.getLatitud(),dC.getLongitud());
                        mMap.addMarker(new MarkerOptions().position(sydney).title(dC.getNombre()));
                        // LatLng sydney = new LatLng(-34, 151);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,1));
                      mMap.getUiSettings().setZoomControlsEnabled(true);
 }
                } else {
                    Log.e(TAG, "onResponse " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DatosColombia>> call, Throwable t) {
                Log.e(TAG, "onFailure " + t.getMessage());

            }
        });
    }}



/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */
       /*public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/

