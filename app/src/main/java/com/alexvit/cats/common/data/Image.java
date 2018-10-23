package com.alexvit.cats.common.data;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id") public final String id;
    @SerializedName("url") public final String url;

    public Image(String id, String url) {
        this.id = id;
        this.url = url;
    }
}
