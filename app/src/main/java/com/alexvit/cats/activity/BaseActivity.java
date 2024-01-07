package com.alexvit.cats.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alexvit.cats.SafeSubscriber;

public abstract class BaseActivity extends AppCompatActivity implements SafeSubscriber {

    @Override
    protected void onStop() {
        subs.clear();
        super.onStop();
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
