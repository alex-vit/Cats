package com.alexvit.cats.features.list;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alexvit.cats.R;
import com.alexvit.cats.base.BaseActivity;
import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.util.Screen;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends BaseActivity<ListPresenter>
        implements ListContract.View {

    private static final String TAG = ListActivity.class.getSimpleName();

    private static final int COL_WIDTH = 200;

    @Inject
    ListPresenter presenter;

    private ListAdapter adapter;
    private RecyclerView rvThumbnails;

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
        Log.d(TAG, "Got " + images.size() + " cats");
        adapter.setImages(images);
    }

    private void bindViews() {
        rvThumbnails = findViewById(R.id.rv_thumbnails);
    }

    private void initRecycler() {
        adapter = new ListAdapter();
        rvThumbnails.setAdapter(adapter);

        int columns = Screen.columnCount(this, COL_WIDTH);
        rvThumbnails.setLayoutManager(new GridLayoutManager(
                this, columns, GridLayoutManager.VERTICAL, false));
    }
}
