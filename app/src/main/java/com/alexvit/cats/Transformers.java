package com.alexvit.cats;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Transformers {

    private Transformers() {
    }

    // TODO inline as: repos subscribe(IO); observers observe(UI)
    public static <A> ObservableTransformer<A, A> schedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
