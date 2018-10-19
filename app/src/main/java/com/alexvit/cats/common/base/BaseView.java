package com.alexvit.cats.common.base;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public interface BaseView {

    void displayError(Throwable throwable);

    void showLoading(boolean isLoading);

    void close();

}
