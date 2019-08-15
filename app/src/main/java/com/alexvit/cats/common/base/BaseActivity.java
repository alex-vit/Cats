package com.alexvit.cats.common.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

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

        setupViews();

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

    protected abstract void setupViews();

    protected void toast(@StringRes int stringRes) {
        toast(getString(stringRes));
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
