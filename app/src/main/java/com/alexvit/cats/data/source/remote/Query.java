package com.alexvit.cats.data.source.remote;

/**
 * Created by Aleksandrs Vitjukovs on 11/5/2017.
 */

public final class Query {

    private Query() {
    }

    public static final class Size {

        private Size() {
        }

        public static final String SMALL = "small";
        public static final String MEDIUM = "med";
        public static final String FULL = "full";
    }

    public static final class Score {

        private Score() {
        }

        public static final int LOVE = 10;
        public static final int HATE = 1;
    }

}
