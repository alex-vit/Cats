package com.alexvit.cats.base;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alexvit.cats.App;
import com.alexvit.cats.di.component.ActivityComponent;
import com.alexvit.cats.di.component.DaggerActivityComponent;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public abstract class BaseActivity<Presenter extends BasePresenter>
        extends AppCompatActivity
        implements BaseView {

    @Override
    protected void onStop() {
        getPresenter().stop();
        super.onStop();
    }

    protected abstract Presenter getPresenter();

    @Override
    public void onError(Throwable throwable) {
        onError(throwable.getMessage());
    }

    @Override
    public void onError(String errorMessage) {
        toast(errorMessage);
    }

    protected abstract void bindViews();

    protected void toast(@StringRes int stringRes) {
        toast(getString(stringRes));
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected ActivityComponent buildComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(App.component(this))
                .build();
    }

}
