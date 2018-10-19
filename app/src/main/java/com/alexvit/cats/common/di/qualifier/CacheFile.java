package com.alexvit.cats.common.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheFile {
}
