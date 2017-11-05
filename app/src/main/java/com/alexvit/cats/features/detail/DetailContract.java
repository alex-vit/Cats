package com.alexvit.cats.features.detail;

import com.alexvit.cats.base.BaseView;
import com.alexvit.cats.data.model.api.Image;

/**
 * Created by Aleksandrs Vitjukovs on 11/4/2017.
 */

interface DetailContract {

    interface Presenter {
        void setId(String id);
    }

    interface View extends BaseView {
        void displayImage(Image image);
    }

}
