package com.explore.lin.didemo.javaDIdemo;

import javax.inject.Inject;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

public class War {
    private Starks starks;
    private Boltons boltons;

    //DI - getting dependencies from else where via constructor
    @Inject
    public War(Starks starks, Boltons bolton){
        this.starks = starks;
        this.boltons = bolton;
    }

    public void prepare(){
        starks.prepareForWar();
        boltons.prepareForWar();
    }

    public void report(){
        starks.reportForWar();
        boltons.reportForWar();
    }
}
