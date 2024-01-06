package com.alexvit.cats;

import android.content.Context;
import android.content.SharedPreferences;

import com.alexvit.cats.di.scope.ApplicationScope;
import com.google.firebase.analytics.FirebaseAnalytics;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Module
public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationScope
    FirebaseAnalytics firebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Provides
    @ApplicationScope
    SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @ApplicationScope
    Context context(App app) {
        return app.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    App app() {
        return app;
    }

}
