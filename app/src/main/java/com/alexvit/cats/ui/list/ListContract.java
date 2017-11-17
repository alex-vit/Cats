package com.alexvit.cats.ui.list;

import com.alexvit.cats.base.BaseView;
import com.alexvit.cats.data.model.Image;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

interface ListContract {

    interface Presenter {
        void loadRandomImages();

        void refresh();
    }

    interface View extends BaseView {
        void displayImages(List<Image> images);
        void showLoading(boolean show);
    }

}