package com.alexvit.cats.list;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alexvit.cats.App;
import com.alexvit.cats.R;
import com.alexvit.cats.common.base.BaseActivity;
import com.alexvit.cats.common.data.Image;
import com.alexvit.cats.common.rx.ActivityModule;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable.UnsubscribeOn;
import com.alexvit.cats.common.traits.HasComponent;
import com.alexvit.cats.common.traits.HasViewModel;
import com.alexvit.cats.common.util.Screen;
import com.alexvit.cats.detail.DetailActivity;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ListActivity extends BaseActivity implements
        HasComponent<ListComponent>,
        HasViewModel<ListViewModel>,
        ListAdapter.OnItemClickListener {

    private static final int COL_WIDTH = 200;

    @Inject
    ListViewModel viewModel;

    private ListAdapter adapter;
    private RecyclerView thumbnails;
    private SwipeRefreshLayout refresh;
    private AppBarLayout appBar;

    private Boolean insetApplied = false;

    private LifecycleCompositeDisposable subs = new LifecycleCompositeDisposable(getLifecycle(),
            UnsubscribeOn.PAUSE);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void setupViews() {
        thumbnails = findViewById(R.id.rv_thumbnails);

        refresh = findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.primary, R.color.accent);
        refresh.setOnRefreshListener(viewModel::refresh);

        appBar = findViewById(R.id.app_bar);
        appBar.addOnOffsetChangedListener((v, offset) -> {
            if (offset == 0) onToolbarExpanded();
            else if (Math.abs(offset) == appBar.getTotalScrollRange()) onToolbarCollapsed();
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

        initRecycler();
    }

    @Override
    public ListComponent buildComponent() {
        return DaggerListComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void inject(ListComponent component) {
        component.inject(this);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public LifecycleCompositeDisposable getSubs() {
        return subs;
    }

    @Override
    public ListViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void observe(ListViewModel viewModel) {
        subscribe(viewModel.getState(), this::onState);
    }

    @Override
    public void onItemClicked(Image image, ImageView shared) {
        launchDetails(image.id, shared);
    }

    private void onState(ListViewModel.State state) {
        showLoading(state.loading);
        if (state.images != null) displayImages(state.images);
    }

    private void showLoading(boolean isLoading) {
        refresh.setRefreshing(isLoading);
    }

    private void displayImages(List<Image> images) {
        adapter.setImages(images);
    }

    private void initRecycler() {
        adapter = new ListAdapter(this);
        thumbnails.setAdapter(adapter);

        int columns = Math.max(2, Screen.columnCount(this, COL_WIDTH));
        thumbnails.setLayoutManager(new GridLayoutManager(
                this, columns, GridLayoutManager.VERTICAL, false));
    }

    private void launchDetails(String id, ImageView shared) {
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
