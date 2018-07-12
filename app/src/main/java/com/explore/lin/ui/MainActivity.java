package com.explore.lin.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.explore.lin.R;
import com.explore.lin.adapter.RandomUserAdapter;
import com.explore.lin.interfaces.RandomUsersApi;
import com.explore.lin.model.RandomUsers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    RecyclerView recyclerView;
    RandomUserAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor httpLoggingInterceptor = new
                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        populateUsers();

    }

    private void populateUsers() {
        getRandomUserService().getRandomUsers(10)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RandomUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(RandomUsers randomUsers) {
                        System.out.println("onNext");
                        mAdapter = new RandomUserAdapter();
                        mAdapter.setItems(randomUsers.getResults());
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                        Timber.i(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");

                    }
                });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public RandomUsersApi getRandomUserService() {
        return retrofit.create(RandomUsersApi.class);
    }
}
