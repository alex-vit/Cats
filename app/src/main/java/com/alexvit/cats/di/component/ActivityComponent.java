package com.alexvit.cats.di.component;

import com.alexvit.cats.di.module.ActivityContextModule;
import com.alexvit.cats.di.module.FirebaseModule;
import com.alexvit.cats.di.module.PresenterModule;
import com.alexvit.cats.di.scope.ActivityScope;
import com.alexvit.cats.ui.detail.DetailActivity;
import com.alexvit.cats.ui.list.ListActivity;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {ActivityContextModule.class, PresenterModule.class, FirebaseModule.class}
)
public interface ActivityComponent {

    void inject(ListActivity listActivity);

    void inject(DetailActivity detailActivity);

}
