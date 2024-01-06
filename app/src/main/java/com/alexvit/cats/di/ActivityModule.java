package com.alexvit.cats.di;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.alexvit.cats.di.scope.ActivityScope;

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
