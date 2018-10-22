package com.alexvit.cats.list;

import com.alexvit.cats.ApplicationComponent;
import com.alexvit.cats.common.base.BaseComponent;
import com.alexvit.cats.common.di.scope.ActivityScope;
import com.alexvit.cats.common.rx.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {ActivityModule.class, ListModule.class}
)
public interface ListComponent extends BaseComponent {

    void inject(ListActivity listActivity);

}
