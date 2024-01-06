package com.alexvit.cats;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    private Analytics() {}

    private static FirebaseAnalytics instance() {
        return App.getComponent().firebaseAnalytics();
    }

    public static void itemListView() {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "all");
        instance().logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, params);
    }

    public static void itemView(String id) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        instance().logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);
    }

    public static void itemShare(String id) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        instance().logEvent(FirebaseAnalytics.Event.SHARE, params);
    }

}
