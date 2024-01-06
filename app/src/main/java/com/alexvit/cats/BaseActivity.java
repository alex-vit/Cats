package com.alexvit.cats;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements SafeSubscriber {

    @Override
    protected void onStop() {
        subs.clear();
        super.onStop();
    }

    @Override
    public abstract void onError(Throwable throwable);
}
