package com.alexvit.cats.di.module;

import android.content.Context;

import com.alexvit.cats.di.qualifier.ActivityContext;
import com.alexvit.cats.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexander.vitjukov on 13.11.2017.
 */

@Module
public class ActivityContextModule {

    private final Context context;

    public ActivityContextModule(@ActivityContext Context context) {
        this.context = context;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    Context activityContext() {
        return context;
    }
}
