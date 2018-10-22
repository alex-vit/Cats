package com.alexvit.cats.common;

import android.os.Bundle;

import com.alexvit.cats.App;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    private Analytics() {}

    private static FirebaseAnalytics instance() {
        return App.getComponent().firebaseAnalytics();
    }

    public static void viewItemList() {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "all");
        instance().logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, params);
    }

}
