package com.alexvit.cats.base;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public interface BaseView {

    void onError(String message);

    void onError(Throwable throwable);

}
