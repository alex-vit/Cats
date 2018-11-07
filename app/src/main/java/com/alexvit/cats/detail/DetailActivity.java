package com.alexvit.cats.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.alexvit.cats.App;
import com.alexvit.cats.GlideApp;
import com.alexvit.cats.R;
import com.alexvit.cats.common.base.BaseActivity;
import com.alexvit.cats.common.data.Image;
import com.alexvit.cats.common.rx.ActivityModule;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable;
import com.alexvit.cats.common.rx.LifecycleCompositeDisposable.UnsubscribeOn;
import com.alexvit.cats.common.traits.HasComponent;
import com.alexvit.cats.common.traits.HasViewModel;
import com.alexvit.cats.detail.DetailViewModel.State;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends BaseActivity implements
        HasComponent<DetailComponent>,
        HasViewModel<DetailViewModel> {

    private static final String KEY_ID = "KEY_ID";
    private static final String KEY_UI_VISIBILITY = "KEY_UI_VISIBILITY";

    private static final int FULLSCREEN = View.SYSTEM_UI_FLAG_IMMERSIVE
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_FULLSCREEN;
    private static final int FULLSCREEN_HIDE_NAV = View.SYSTEM_UI_FLAG_IMMERSIVE
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

    private final LifecycleCompositeDisposable subs = new LifecycleCompositeDisposable(getLifecycle(),
            UnsubscribeOn.PAUSE);
    @Inject
    DetailViewModel viewModel;

    private ImageView ivFull;
    private Toolbar toolbar;

    private boolean isUiShown = true;
    private Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isUiShown = savedInstanceState.getBoolean(KEY_UI_VISIBILITY, isUiShown);
        }
        updateUiVisibility();

        String id = getIntent().getStringExtra(KEY_ID);
        if (id != null) {
            viewModel.load(id);
        } else {
            throw new IllegalArgumentException("No ID was given.");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_UI_VISIBILITY, isUiShown);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) showUi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
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
        View root = findViewById(R.id.root);
        root.setOnClickListener(__ -> {
            isUiShown = !isUiShown;
            updateUiVisibility();
        });
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
        if (state.image != null) displayImage(state.image);
    }

    public static Intent getIntent(Activity activity, String id) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                .into(ivFull);
    }

    private void shareImage(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");

        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }

    private void updateUiVisibility() {
        if (isUiShown) showUi();
        else hideUi();

        float alpha = (isUiShown) ? 1 : 0;
        toolbar.animate().alpha(alpha);
    }

    private void showUi() {
        getWindow().getDecorView().setSystemUiVisibility(FULLSCREEN);
    }

    private void hideUi() {
        getWindow().getDecorView().setSystemUiVisibility(FULLSCREEN_HIDE_NAV);
    }

}
