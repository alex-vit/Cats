package com.alexvit.cats.ui.detail;

import com.alexvit.cats.base.BaseView;
import com.alexvit.cats.data.model.Image;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

interface DetailContract {

    interface Presenter {
        void setId(String id);

        void vote(String id, int score);
    }

    interface View extends BaseView {

        void displayImage(Image image);

        void displayUpVote();

        void displayDownVote();
        void resetVoteButtons();

        void toastUpVote();

        void toastDownVote();
    }

}
