package com.alexvit.cats.list;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.alexvit.cats.App;
import com.alexvit.cats.R;
import com.alexvit.cats.common.base.BaseActivity;
import com.alexvit.cats.common.rx.ActivityModule;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable.UnsubscribeOn;
import com.alexvit.cats.common.traits.HasComponent;
import com.alexvit.cats.common.traits.HasViewModel;
import com.alexvit.cats.data.model.Image;
import com.alexvit.cats.detail.DetailActivity;
import com.alexvit.cats.util.Screen;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends BaseActivity implements
        HasComponent<ListComponent>,
        HasViewModel<ListViewModel>,
        ListAdapter.OnItemClickListener {

    private static final int COL_WIDTH = 200;

    @Inject
    FirebaseAnalytics analytics;

    @Inject
    ListViewModel viewModel;

    private ListAdapter adapter;
    private RecyclerView rvThumbnails;
    private SwipeRefreshLayout refresh;

    private LifecycleCompositeDisposable subs = new LifecycleCompositeDisposable(getLifecycle(),
            UnsubscribeOn.PAUSE);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void bindViews() {
        rvThumbnails = findViewById(R.id.rv_thumbnails);

        refresh = findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.primary, R.color.accent);
        refresh.setOnRefreshListener(viewModel::refresh);

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

}
