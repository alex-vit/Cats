package com.alexvit.cats.di.module;

import android.content.Context;

import com.alexvit.cats.di.qualifier.ApplicationContext;
import com.alexvit.cats.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    Context context() {
        return context;
    }
}
