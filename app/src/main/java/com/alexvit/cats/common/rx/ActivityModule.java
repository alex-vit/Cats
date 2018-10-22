package com.alexvit.cats.common.rx;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.cats.common.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    AppCompatActivity activity() {
        return activity;
    }

    @Provides
    @ActivityScope
    Lifecycle lifecycle(AppCompatActivity activity) {
        return activity.getLifecycle();
    }

}
