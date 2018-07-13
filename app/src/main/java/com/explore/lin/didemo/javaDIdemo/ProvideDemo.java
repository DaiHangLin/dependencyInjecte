package com.explore.lin.didemo.javaDIdemo;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * @author lin
 * @date 18/7/13
 * @license Copyright (c) 2016 那镁克
 */

class AP {
    @Inject
    public AP() {

    }
    @Inject
    BP bp;

    void act() {
        bp.prepare();
    }
}

class BP {
    void prepare() {
        System.out.println("bp.prepare()");
    }
}

@Component(modules = {APModule.class})
interface APComponent{
    AP ap();
}

@Module
class APModule {

    @Provides
    BP providerBP() {
        return new BP();
    }
}

public class ProvideDemo {
    public static void main(String[] main) {
        DaggerAPComponent.create().ap().act();
    }
}
