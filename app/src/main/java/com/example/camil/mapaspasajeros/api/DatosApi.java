package com.example.camil.mapaspasajeros.api;

import com.example.camil.mapaspasajeros.models.DatosColombia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by camil on 26/10/2017.
 */

public interface DatosApi {
    @GET("efg9-8jrp.json")
    Call<List<DatosColombia>> obtenerListaDatoss();

}
