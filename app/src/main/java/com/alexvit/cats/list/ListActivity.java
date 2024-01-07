package com.alexvit.cats.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alexvit.cats.App;
import com.alexvit.cats.R;
import com.alexvit.cats.Screen;
import com.alexvit.cats.activity.ActivityModule;
import com.alexvit.cats.activity.BaseActivity;
import com.alexvit.cats.data.Image;
import com.alexvit.cats.detail.DetailActivity;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class ListActivity extends BaseActivity implements ImageListAdapter.OnImageClickListener {

    @Inject
    ListViewModel viewModel;

    private ImageListAdapter adapter;
    private SwipeRefreshLayout refresh;
    private AppBarLayout appBar;
    private int appBarOffset;
    private Boolean insetApplied = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DaggerListComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView thumbnails = findViewById(R.id.rv_thumbnails);

        refresh = findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.primary, R.color.accent);
        refresh.setOnRefreshListener(viewModel::refresh);

        appBar = findViewById(R.id.app_bar);
        appBar.addOnOffsetChangedListener((v, offset) -> {
            if (offset == appBarOffset) return;
            appBarOffset = offset;
            if (offset == 0) {
                onToolbarExpanded();
            } else if (Math.abs(offset) == appBar.getTotalScrollRange()) {
                onToolbarCollapsed();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.toolbar), (v, windowInsets) -> {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, v.getPaddingTop(), insets.right, v.getPaddingBottom());
            return WindowInsetsCompat.CONSUMED;
        });

        View root = findViewById(R.id.root);
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            if (!insetApplied) {
                insetApplied = true;
                int top = insets.getSystemWindowInsetTop();
                Log.d("insets", String.valueOf(top));

                Toolbar toolbar = findViewById(R.id.toolbar);
                AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.height += top;
                toolbar.setLayoutParams(layoutParams);
            }
            return insets;
        });

        adapter = new ImageListAdapter(this);
        thumbnails.setAdapter(adapter);

        int columns = Math.max(2, Screen.columnCount(this, 200));
        var grid = Objects.requireNonNull((GridLayoutManager) thumbnails.getLayoutManager());
        grid.setSpanCount(columns);
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribe(viewModel.getState(), this::onState);
    }

    @Override
    public void onImageClick(Image image, ImageView shared) {
        launchDetails(image.id(), shared);
    }

    private void onState(ListState state) {
        showLoading(state.loading());
        if (state.images() != null) displayImages(state.images());
    }

    private void showLoading(boolean isLoading) {
        refresh.setRefreshing(isLoading);
    }

    private void displayImages(@NonNull List<Image> images) {
        adapter.submitList(images);
    }

    private void launchDetails(String id, ImageView shared) {
        @SuppressLint("UnsafeIntentLaunch")
        Intent intent = DetailActivity.getIntent(this, id);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, shared, "cat_image");

        startActivity(intent, options.toBundle());
    }

    private void onToolbarCollapsed() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE

                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    private void onToolbarExpanded() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

}
