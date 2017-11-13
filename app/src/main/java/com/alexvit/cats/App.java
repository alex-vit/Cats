package com.alexvit.cats;

import android.app.Application;
import android.content.Context;

import com.alexvit.cats.di.component.ApplicationComponent;
import com.alexvit.cats.di.component.DaggerApplicationComponent;
import com.alexvit.cats.di.module.ContextModule;
import com.google.firebase.FirebaseApp;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        initFirebase();
    }

    public static ApplicationComponent component(Context context) {
        App app = (App) context.getApplicationContext();
        return app.component;
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }

}
