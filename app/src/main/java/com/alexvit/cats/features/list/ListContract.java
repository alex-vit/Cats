package com.alexvit.cats.features.list;

import com.alexvit.cats.base.BaseView;
import com.alexvit.cats.data.model.api.Image;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

public class ListContract {

    interface Presenter {
        void loadRandomImages();
    }

    interface View extends BaseView {
        void displayImages(List<Image> images);
    }

}
