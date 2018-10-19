package com.alexvit.cats.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alexvit.cats.util.Error;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public abstract class BaseActivity<Component extends BaseComponent, Presenter extends BasePresenter<? extends BaseView>>
        extends AppCompatActivity
        implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        bindViews();
        inject(getComponent());
        attach(getPresenter());
    }

    @Override
    protected void onStop() {
        getPresenter().stop();
        super.onStop();
    }

    @Override
    public void displayError(Throwable throwable) {
        final String errorMessage = Error.messageIdForThrowable(this, throwable);
        toast(errorMessage);
    }

    @Override
    public void close() {
        finish();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void bindViews();

    protected abstract Component getComponent();

    protected abstract void inject(Component component);

    protected abstract Presenter getPresenter();

    protected abstract void attach(Presenter presenter);

    protected void toast(@StringRes int stringRes) {
        toast(getString(stringRes));
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
