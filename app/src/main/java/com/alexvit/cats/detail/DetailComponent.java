package com.alexvit.cats.detail;

import com.alexvit.cats.ApplicationComponent;
import com.alexvit.cats.di.ActivityModule;
import com.alexvit.cats.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {ActivityModule.class, DetailModule.class}
)
interface DetailComponent {
    void inject(DetailActivity detailActivity);
}
