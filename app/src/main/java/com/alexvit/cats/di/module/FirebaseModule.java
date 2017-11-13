package com.alexvit.cats.di.module;

import android.content.Context;

import com.alexvit.cats.di.qualifier.ActivityContext;
import com.alexvit.cats.di.scope.ActivityScope;
import com.google.firebase.analytics.FirebaseAnalytics;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexander.vitjukov on 13.11.2017.
 */

@Module
public class FirebaseModule {

    @Provides
    @ActivityScope
    FirebaseAnalytics firebaseAnalytics(@ActivityContext Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

}
