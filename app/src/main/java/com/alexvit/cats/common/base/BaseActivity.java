package com.alexvit.cats.common.base;

import androidx.appcompat.app.AppCompatActivity;

import com.alexvit.cats.common.rx.SafeSubscriber;

public abstract class BaseActivity extends AppCompatActivity implements SafeSubscriber {

    @Override
    protected void onStop() {
        subs.clear();
        super.onStop();
    }

    @Override
    public abstract void onError(Throwable throwable);
}
