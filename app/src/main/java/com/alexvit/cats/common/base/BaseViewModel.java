package com.alexvit.cats.common.base;

import com.alexvit.cats.common.rx.LifecycleCompositeDisposable;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable.UnsubscribeOn;
import com.alexvit.cats.common.traits.SafeSubscriber;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseViewModel<State> extends ViewModel implements LifecycleObserver, SafeSubscriber {

    private final LifecycleCompositeDisposable subs;
    private final BehaviorSubject<State> state = BehaviorSubject.createDefault(defaultState());
    protected State currentState = defaultState();

    public BaseViewModel(Lifecycle lifecycle) {
        subs = new LifecycleCompositeDisposable(lifecycle, UnsubscribeOn.STOP);
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public final void onStart_() {
        onStart();
    }

    @Override
    public LifecycleCompositeDisposable getSubs() {
        return subs;
    }

    protected abstract State defaultState();

    protected void onStart() {}

    public Observable<State> getState() {
        return state.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    protected void setState(State newState) {
        state.onNext(newState);
        currentState = newState;
    }

}
