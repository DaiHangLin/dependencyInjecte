package com.explore.lin.didemo.javaDIdemo;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

public class War {
    private Starks starks;

    private Boltons boltons;

    public War(){
        starks = new Starks();
        boltons = new Boltons();

        starks.prepareForWar();
        starks.reportForWar();
        boltons.prepareForWar();
        boltons.reportForWar();
    }
}
