package com.alexvit.cats.list;

import androidx.annotation.Nullable;

import com.alexvit.cats.data.Image;

import java.util.List;

public record ListState(
        @Nullable List<Image> images,
        boolean loading,
        @Nullable String error
) {}
