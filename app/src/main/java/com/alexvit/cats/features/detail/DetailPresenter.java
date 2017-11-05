package com.alexvit.cats.features.detail;

import android.util.Log;

import com.alexvit.cats.base.BasePresenter;
import com.alexvit.cats.data.CatRepository;
import com.alexvit.cats.data.model.api.Vote;
import com.alexvit.cats.data.source.remote.Query;

import java.util.Map;

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
    public void attach(DetailContract.View view) {
        super.attach(view);

        Observable<Map<String, Integer>> observable = repository.getVotes();
        subscribe(observable, map -> {
            for (Map.Entry<String, Integer> pair : map.entrySet()) {
                Log.d("Presenter", String.format(
                        "Vote(id = %s, score = %d)", pair.getKey(), pair.getValue()));
            }
        });
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
                    if (score == Query.Score.LOVE) {
                        view.displayUpvote();
                    } else if (score == Query.Score.HATE) {
                        view.displayDownvote();
                    }
                });
    }
}
