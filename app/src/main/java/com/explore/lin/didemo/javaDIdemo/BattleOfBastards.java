package com.explore.lin.didemo.javaDIdemo;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

public class BattleOfBastards {
    public static void main(String[] args){
//        Starks starks = new Starks();
//        Boltons boltons = new Boltons();
//
//        War war = new War(starks,boltons);
//        war.prepare();
//        war.report();
        BattleComponent battleComponent = DaggerBattleComponent.create();
        War war = battleComponent.getWar();
        war.prepare();
        war.report();
    }
}
