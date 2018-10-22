package com.alexvit.cats.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alexvit.cats.common.traits.HasComponent;
import com.alexvit.cats.common.traits.HasViewModel;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        if (this instanceof HasComponent<?>) {
            BaseComponent baseComponent = ((HasComponent<?>) this).buildComponent();
            //noinspection unchecked
            ((HasComponent) this).inject(baseComponent);
        }

        bindViews();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this instanceof HasViewModel<?>) {
            BaseViewModel viewModel = ((HasViewModel<?>) this).getViewModel();
            //noinspection unchecked
            ((HasViewModel) this).observe(viewModel);
        }
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void bindViews();

    protected void toast(@StringRes int stringRes) {
        toast(getString(stringRes));
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
