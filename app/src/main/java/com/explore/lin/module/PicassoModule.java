package com.explore.lin.module;

import android.content.Context;

import com.explore.lin.interfaces.ApplicationContext;
import com.explore.lin.interfaces.RandomUserApplicationScope;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

@Module(includes = OkHttpClientModule.class)
public class PicassoModule {

    @RandomUserApplicationScope
    @Provides
    public Picasso picasso(@ApplicationContext Context context, OkHttp3Downloader okHttp3Downloader){
        return new Picasso.Builder(context).
                downloader(okHttp3Downloader).
                build();
    }

    @Provides
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient){
        return new OkHttp3Downloader(okHttpClient);
    }

}
