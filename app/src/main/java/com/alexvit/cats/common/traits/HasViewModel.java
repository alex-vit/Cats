package com.alexvit.cats.common.traits;

import com.alexvit.cats.common.base.BaseViewModel;

import io.reactivex.annotations.NonNull;

public interface HasViewModel<ViewModel extends BaseViewModel> extends SafeSubscriber {

    @NonNull
    ViewModel getViewModel();

    void observe(ViewModel viewModel);

}
