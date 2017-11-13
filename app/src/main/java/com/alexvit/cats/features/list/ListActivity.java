package com.alexvit.cats.features.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.alexvit.cats.R;
import com.alexvit.cats.base.BaseActivity;
import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.di.component.ActivityComponent;
import com.alexvit.cats.features.detail.DetailActivity;
import com.alexvit.cats.util.Screen;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends BaseActivity<ListPresenter>
        implements ListContract.View, ListAdapter.OnItemClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = ListActivity.class.getSimpleName();

    private static final int COL_WIDTH = 200;

    @Inject
    ListPresenter presenter;

    @Inject
    FirebaseAnalytics analytics;

    private ListAdapter adapter;
    private RecyclerView rvThumbnails;
    private SwipeRefreshLayout refresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void bindViews() {
        rvThumbnails = findViewById(R.id.rv_thumbnails);

        refresh = findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.primary, R.color.accent);
        refresh.setOnRefreshListener(this::refresh);

        initRecycler();
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected ListPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void attach(ListPresenter presenter) {
        presenter.attach(this);
    }

    @Override
    public void displayImages(List<Image> images) {
        adapter.setImages(images);
        logViewItemList();
    }

    @Override
    public void showLoading(boolean isLoading) {
        refresh.setRefreshing(isLoading);
    }

    @Override
    public void onItemClicked(Image image, ImageView shared) {
        launchDetails(image.id, shared);
    }

    private void initRecycler() {
        adapter = new ListAdapter(this);
        rvThumbnails.setAdapter(adapter);

        int columns = Math.max(2, Screen.columnCount(this, COL_WIDTH));
        rvThumbnails.setLayoutManager(new GridLayoutManager(
                this, columns, GridLayoutManager.VERTICAL, false));
    }

    private void launchDetails(String id, ImageView shared) {
        Intent intent = DetailActivity.getIntent(this, id);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, shared, "cat_image");

        startActivity(intent, options.toBundle());
    }

    private void refresh() {
        presenter.refresh();
    }

    private void logViewItemList() {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "all");
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, params);
    }

}
