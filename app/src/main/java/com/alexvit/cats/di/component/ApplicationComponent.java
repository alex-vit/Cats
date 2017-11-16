package com.alexvit.cats.di.component;

import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.di.module.ApplicationModule;
import com.alexvit.cats.di.module.CatRepositoryModule;
import com.alexvit.cats.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class, CatRepositoryModule.class})
public interface ApplicationComponent {

    CatRepository catRepository();

}
