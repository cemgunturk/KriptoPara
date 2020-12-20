package com.example.kriptopara.Service;

import com.example.kriptopara.Model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    @GET("prices?key=a2e754e9d9834aa8b816b837d5d29df7")
    Observable<List<CryptoModel>> getData();


    //Call<List<CryptoModel>> getData();

}
