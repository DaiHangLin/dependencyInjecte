package com.explore.lin.didemo.javaDIdemo;

import dagger.Component;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

@Component
interface BattleComponent {
    War getWar();
    //adding more methods
    Starks getStarks();
    Boltons getBoltons();
}
