package com.alexvit.cats.detail;

import com.alexvit.cats.ApplicationComponent;
import com.alexvit.cats.common.base.BaseComponent;
import com.alexvit.cats.common.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {DetailModule.class}
)
interface DetailComponent extends BaseComponent {

    void inject(DetailActivity detailActivity);

}
