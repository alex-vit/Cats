package com.alexvit.cats;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alexvit.cats.di.qualifier.ApplicationContext;
import com.alexvit.cats.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    SharedPreferences sharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    Context context() {
        return context;
    }

}
