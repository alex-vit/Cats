package com.alexvit.cats.detail;

import androidx.annotation.Nullable;

import com.alexvit.cats.data.Image;

record DetailState(
        @Nullable Image image,
        boolean loading,
        boolean error
) {}
