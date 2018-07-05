package com.alexvit.cats.ui.detail;

import com.alexvit.cats.base.BasePresenter;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.model.Vote;
import com.alexvit.cats.data.source.remote.Contract;
import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class DetailPresenter extends BasePresenter<DetailContract.View>
        implements DetailContract.Presenter {

    private final CatRepository repository;

    public DetailPresenter(CatRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setId(String id) {
        subscribe(repository.getImageById(id), view::displayImage);
    }

    @Override
    public void vote(String id, int score) {
        Observable<Vote> observable = repository.vote(id, score);
        subscribe(observable,
                __ -> {
                    view.resetVoteButtons();
                    switch (score) {
                        case Contract.SCORE_LOVE:
                            view.displayUpVote();
                            view.toastUpVote();
                            break;
                        case Contract.SCORE_HATE:
                            view.displayDownVote();
                            view.toastDownVote();
                            break;
                    }
                });
    }

}
