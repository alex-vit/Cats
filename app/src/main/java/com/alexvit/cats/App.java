package com.alexvit.cats;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class App extends Application {

    private static App INSTANCE;
    private ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return INSTANCE.component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        initFirebase();
        initFabric();
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }

    private void initFabric() {
        Fabric.with(this, new Crashlytics());
    }

}
