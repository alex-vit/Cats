package com.alexvit.cats;

import android.app.Application;
import android.os.Looper;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;

import io.fabric.sdk.android.Fabric;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        setAsyncMainThreadScheduler();

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

    private void setAsyncMainThreadScheduler() {
        Scheduler asyncScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> asyncScheduler);
    }

}
