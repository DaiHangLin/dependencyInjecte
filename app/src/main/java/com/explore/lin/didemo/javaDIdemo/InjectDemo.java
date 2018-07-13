package com.explore.lin.didemo.javaDIdemo;

import javax.inject.Inject;

import dagger.Component;

/**
 * @author lin
 * @date 18/7/13
 * @license Copyright (c) 2016 那镁克
 */

class A {
    @Inject
    public A() {
    }

    @Inject
    B b;

    void act() {
        b.prepare();
    }
}

class B {
    @Inject
    public B() {

    }

    void prepare() {
        System.out.println("b.prepare()");
    }
}

@Component
interface AComponent {
    A a();
}

public class InjectDemo {

    public static void main(String[] args) {
        DaggerAComponent.create().a().act();
    }
}
