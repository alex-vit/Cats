package com.alexvit.cats;

import androidx.annotation.CallSuper;
import androidx.lifecycle.ViewModel;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class BaseViewModel<State> extends ViewModel implements SafeSubscriber {

    private final BehaviorSubject<State> state = BehaviorSubject.createDefault(defaultState());
    protected State currentState = defaultState();

    protected abstract State defaultState();

    public Observable<State> getState() {
        return state.toFlowable(BackpressureStrategy.LATEST).toObservable();
    }

    protected void setState(State newState) {
        state.onNext(newState);
        currentState = newState;
    }

    @CallSuper
    @Override
    protected void onCleared() {
        super.onCleared();
        subs.clear();
    }
}
