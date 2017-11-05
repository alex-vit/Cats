package com.alexvit.cats.features.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.alexvit.cats.R;
import com.alexvit.cats.base.BaseActivity;
import com.alexvit.cats.data.model.api.Image;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity<DetailPresenter>
        implements DetailContract.View {

    private static final String KEY_ID = "KEY_ID";

    private String id;

    @Inject
    DetailPresenter presenter;

    ImageView ivFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        id = getIntent().getStringExtra(KEY_ID);
        if (id == null) {
            toast("No ID was given.");
            finish();
        }

        bindViews();

        buildComponent().inject(this);
        presenter.attach(this);
        presenter.setId(id);
    }

    @Override
    protected DetailPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void bindViews() {
        ivFull = findViewById(R.id.iv_full);
    }

    @Override
    public void displayImage(Image image) {
        setTitle(image.id);
        Picasso.with(this)
                .load(image.url)
                .into(ivFull, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        String message = String.format(
                                getString(R.string.error_failed_to_load), image.id);
                        DetailActivity.this.onError(message);
                        finish();
                    }
                });
    }

    public static Intent getIntent(Activity activity, String id) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

}
