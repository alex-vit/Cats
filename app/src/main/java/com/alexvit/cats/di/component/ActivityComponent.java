package com.alexvit.cats.di.component;

import com.alexvit.cats.di.module.PresenterModule;
import com.alexvit.cats.di.scope.ActivityScope;
import com.alexvit.cats.features.list.ListActivity;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@ActivityScope
@Component(modules = {PresenterModule.class}, dependencies = {ApplicationComponent.class})
public interface ActivityComponent {

    void inject(ListActivity listActivity);

}
