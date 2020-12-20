package com.example.kriptopara.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.kriptopara.Adapter.RecyclerViewAdapter;
import com.example.kriptopara.Model.CryptoModel;
import com.example.kriptopara.R;
import com.example.kriptopara.Service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //https://api.nomics.com/v1/prices?key=a2e754e9d9834aa8b816b837d5d29df7

    ArrayList<CryptoModel> cryptoModels;
    private String baseURL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        getData();
    }


    private void getData() {
        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

        /*
        Call<List<CryptoModel>> call = cryptoAPI.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body();
                    cryptoModelsList = new ArrayList<>(responseList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(adapter);

                    for (CryptoModel cryptoModel : cryptoModelsList) {
                        System.out.println(cryptoModel.currency);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "İnternet bağlantısında bir sorun oluştu.", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    private void handleResponse(List<CryptoModel> cryptoModelList){

        cryptoModels = new ArrayList<>(cryptoModelList);


        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(adapter);
    }

    protected void onDestroy(){
        super.onDestroy();

        compositeDisposable.clear();
    }
}
