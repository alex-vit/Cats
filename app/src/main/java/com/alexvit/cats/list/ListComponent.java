package com.alexvit.cats.list;

import com.alexvit.cats.ApplicationComponent;
import com.alexvit.cats.di.ActivityModule;
import com.alexvit.cats.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {ActivityModule.class, ListModule.class}
)
public interface ListComponent {
    void inject(ListActivity listActivity);
}
