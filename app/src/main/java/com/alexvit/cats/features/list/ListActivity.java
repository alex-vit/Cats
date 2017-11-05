package com.alexvit.cats.features.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.alexvit.cats.R;
import com.alexvit.cats.base.BaseActivity;
import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.features.detail.DetailActivity;
import com.alexvit.cats.util.Screen;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends BaseActivity<ListPresenter>
        implements ListContract.View, ListAdapter.OnItemClickListener {

    private static final String TAG = ListActivity.class.getSimpleName();

    private static final int COL_WIDTH = 200;

    @Inject
    ListPresenter presenter;

    private ListAdapter adapter;
    private RecyclerView rvThumbnails;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bindViews();
        initRecycler();

        buildComponent().inject(this);
        presenter.attach(this);
    }

    @Override
    protected ListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void displayImages(List<Image> images) {
        adapter.setImages(images);
    }

    @Override
    public void showLoading(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClicked(Image image) {
        launchDetails(image.id);
    }

    @Override
    protected void bindViews() {
        rvThumbnails = findViewById(R.id.rv_thumbnails);
        loading = findViewById(R.id.loading);
    }

    private void initRecycler() {
        adapter = new ListAdapter(this);
        rvThumbnails.setAdapter(adapter);

        int columns = Screen.columnCount(this, COL_WIDTH);
        rvThumbnails.setLayoutManager(new GridLayoutManager(
                this, columns, GridLayoutManager.VERTICAL, false));
    }

    private void launchDetails(String id) {
        Intent intent = DetailActivity.getIntent(this, id);
        startActivity(intent);
    }

}
