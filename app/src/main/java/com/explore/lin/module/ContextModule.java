package com.explore.lin.module;

import android.content.Context;

import com.explore.lin.interfaces.ApplicationContext;
import com.explore.lin.interfaces.RandomUserApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @ApplicationContext
    @RandomUserApplicationScope
    @Provides
    public Context context(){ return context.getApplicationContext(); }
}