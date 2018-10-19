package com.alexvit.cats.list;

import com.alexvit.cats.ApplicationComponent;
import com.alexvit.cats.common.base.BaseComponent;
import com.alexvit.cats.common.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {ListModule.class}
)
public interface ListComponent extends BaseComponent {

    void inject(ListActivity listActivity);

}
