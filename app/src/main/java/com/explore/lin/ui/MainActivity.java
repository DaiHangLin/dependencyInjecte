package com.explore.lin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.explore.lin.R;
import com.explore.lin.adapter.RandomUserAdapter;
import com.explore.lin.component.DaggerRandomUserComponent;
import com.explore.lin.component.RandomUserComponent;
import com.explore.lin.interfaces.RandomUsersApi;
import com.explore.lin.model.RandomUsers;
import com.explore.lin.module.ContextModule;
import com.squareup.picasso.Picasso;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @author lin
 * @date 18/7/12
 * @license Copyright (c) 2016 那镁克
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RandomUserAdapter mAdapter;

    Picasso picasso;
    RandomUsersApi randomUsersApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        afterDagger();
        populateUsers();

    }

    private void afterDagger() {
        RandomUserComponent daggerRandomUserComponent = DaggerRandomUserComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        picasso = daggerRandomUserComponent.getPicasso();
        randomUsersApi = daggerRandomUserComponent.getRandomUserService();
    }

    private void populateUsers() {
        randomUsersApi.getRandomUsers(30)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RandomUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(RandomUsers randomUsers) {
                        System.out.println("onNext");
                        mAdapter = new RandomUserAdapter();
                        mAdapter.setItems(randomUsers.getResults());
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                        Timber.i(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");

                    }
                });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
