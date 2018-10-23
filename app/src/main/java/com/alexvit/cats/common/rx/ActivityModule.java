package com.alexvit.cats.common.rx;

import com.alexvit.cats.common.di.scope.ActivityScope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
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
