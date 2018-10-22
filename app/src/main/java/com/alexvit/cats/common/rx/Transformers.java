package com.alexvit.cats.common.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Transformers {

    private Transformers() {
    }

    public static <A> ObservableTransformer<A, A> schedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
