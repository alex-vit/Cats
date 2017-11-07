package com.alexvit.cats.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alexvit.cats.App;
import com.alexvit.cats.di.component.ActivityComponent;
import com.alexvit.cats.di.component.DaggerActivityComponent;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public abstract class BaseActivity<Presenter extends BasePresenter<? extends BaseView>>
        extends AppCompatActivity
        implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        bindViews();

        inject(buildComponent());

        attach(getPresenter());
    }

    @Override
    protected void onStop() {
        getPresenter().stop();
        super.onStop();
    }

    @Override
    public void onError(Throwable throwable) {
        onError(throwable.getMessage());
    }

    @Override
    public void onError(String errorMessage) {
        toast(errorMessage);
        showLoading(false);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void bindViews();

    protected abstract void inject(ActivityComponent activityComponent);

    protected abstract Presenter getPresenter();

    protected abstract void attach(Presenter presenter);

    protected void toast(@StringRes int stringRes) {
        toast(getString(stringRes));
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private ActivityComponent buildComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(App.component(this))
                .build();
    }

}
