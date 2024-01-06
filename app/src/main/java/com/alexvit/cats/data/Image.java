package com.alexvit.cats.data;

import com.google.gson.annotations.SerializedName;

public record Image(@SerializedName("id") String id, @SerializedName("url") String url) {}
