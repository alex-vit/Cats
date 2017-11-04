package com.alexvit.cats.features.list;

import android.os.Bundle;
import android.util.Log;

import com.alexvit.cats.R;
import com.alexvit.cats.base.BaseActivity;
import com.alexvit.cats.data.model.api.Image;

import java.util.List;

import javax.inject.Inject;

public class ListActivity extends BaseActivity<ListPresenter>
        implements ListContract.View {

    private static final String TAG = ListActivity.class.getSimpleName();

    @Inject
    ListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        buildComponent().inject(this);
        presenter.attach(this);

        presenter.loadRandomImages();
    }

    @Override
    protected ListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void displayImages(List<Image> images) {
        Log.d(TAG, "Got " + images.size() + " cats");
    }
}
