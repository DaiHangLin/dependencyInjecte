package com.explore.lin.module;

import android.app.Activity;
import android.content.Context;

import com.explore.lin.interfaces.RandomUserApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

@Module
public class ActivityModule {

    private final Context context;

    ActivityModule(Activity context){
        this.context = context;
    }

    @Named("activity_context")
    @RandomUserApplicationScope
    @Provides
    public Context context(){ return context; }
}
