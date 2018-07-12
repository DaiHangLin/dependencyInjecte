package com.explore.lin.module;

import com.explore.lin.interfaces.RandomUserApplicationScope;
import com.explore.lin.interfaces.RandomUsersApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */


@Module(includes = OkHttpClientModule.class)
public class RandomUsersModule {

    @Provides
    public RandomUsersApi randomUsersApi(Retrofit retrofit){
        return retrofit.create(RandomUsersApi.class);
    }

    @RandomUserApplicationScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://randomuser.me/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }


}
