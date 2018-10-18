package com.alexvit.cats.list;

import com.alexvit.cats.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component()
public interface ListComponent {

    void inject(ListActivity listActivity);

}
