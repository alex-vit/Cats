package com.alexvit.cats.common.rx;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

public class ActivityCompositeDisposable implements DefaultLifecycleObserver, SafeCompositeDisposable {

    public ActivityCompositeDisposable(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        subs.clear();
        Log.d("WTF", "clear @ onStop");
    }
}
