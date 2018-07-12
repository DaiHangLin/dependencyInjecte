package com.explore.lin.interfaces;

import com.explore.lin.model.RandomUsers;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */


public interface RandomUsersApi {

    @GET("api")
    Observable<RandomUsers> getRandomUsers(@Query("results") int size);
}
