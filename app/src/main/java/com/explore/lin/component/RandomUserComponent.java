package com.explore.lin.component;

import com.explore.lin.interfaces.RandomUserApplicationScope;
import com.explore.lin.interfaces.RandomUsersApi;
import com.explore.lin.module.PicassoModule;
import com.explore.lin.module.RandomUsersModule;
import com.squareup.picasso.Picasso;

import dagger.Component;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

@RandomUserApplicationScope
@Component(modules = {RandomUsersModule.class, PicassoModule.class})
public interface RandomUserComponent {

    RandomUsersApi getRandomUserService();

    Picasso getPicasso();
}
