package com.alexvit.cats.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.alexvit.cats.App;
import com.alexvit.cats.GlideApp;
import com.alexvit.cats.R;
import com.alexvit.cats.common.base.BaseActivity;
import com.alexvit.cats.common.rx.ActivityModule;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable.UnsubscribeOn;
import com.alexvit.cats.common.traits.HasComponent;
import com.alexvit.cats.common.traits.HasViewModel;
import com.alexvit.cats.data.model.Image;
import com.alexvit.cats.detail.DetailViewModel.State;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity implements
        HasComponent<DetailComponent>,
        HasViewModel<DetailViewModel> {

    private static final String KEY_ID = "KEY_ID";

    private final LifecycleCompositeDisposable subs = new LifecycleCompositeDisposable(getLifecycle(),
            UnsubscribeOn.PAUSE);
    @Inject
    DetailViewModel viewModel;

    private Image image;

    private ImageView ivFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String id = getIntent().getStringExtra(KEY_ID);
        if (id != null) {
            viewModel.load(id);
        } else {
            throw new IllegalArgumentException("No ID was given.");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.menu_share:
                shareImage(image.url);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setupViews() {
        initToolbar();
        ivFull = findViewById(R.id.iv_full);
    }

    @Override
    public DetailComponent buildComponent() {
        return DaggerDetailComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void inject(DetailComponent component) {
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
    public DetailViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void observe(DetailViewModel viewModel) {
        subscribe(viewModel.getState(), this::onState);
    }

    private void onState(State state) {
        if (state.image != null) displayImage(image);
    }

    public static Intent getIntent(Activity activity, String id) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void displayImage(Image image) {
        this.image = image;
        setTitle(image.id);
        GlideApp.with(this)
                .load(image.url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        applyPalette(((BitmapDrawable) resource).getBitmap());
                        return false;
                    }
                })
                .into(ivFull);
    }

    private void shareImage(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");

        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }

    private void applyPalette(Bitmap bitmap) {
        Palette.from(bitmap).generate(palette -> {
            int defaultColor = ContextCompat.getColor(this, R.color.primary);
            int muted = palette.getMutedColor(defaultColor);
            int color = palette.getVibrantColor(muted);
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setBackgroundDrawable(new ColorDrawable(color));
                getWindow().setStatusBarColor(color);
            }
        });
    }

}
