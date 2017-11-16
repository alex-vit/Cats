package com.alexvit.cats.features.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.alexvit.cats.R;
import com.alexvit.cats.base.BaseActivity;
import com.alexvit.cats.data.model.api.Image;
import com.alexvit.cats.data.source.remote.Contract;
import com.alexvit.cats.di.component.ActivityComponent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity<DetailPresenter>
        implements DetailContract.View {

    @SuppressWarnings("unused")
    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final String KEY_ID = "KEY_ID";

    private String id;
    private Image image;

    @Inject
    DetailPresenter presenter;

    @Inject
    FirebaseAnalytics analytics;

    private ImageView ivFull;
    private ImageView ivUp;
    private ImageView ivDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra(KEY_ID);
        if (id == null) {
            toast("No ID was given.");
            finish();
        }

        presenter.setId(id);
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
                shareText(image.url);
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
    protected void bindViews() {
        ivFull = findViewById(R.id.iv_full);

        ivUp = findViewById(R.id.iv_up);
        ivUp.setOnClickListener(view -> presenter.vote(id, Contract.SCORE_LOVE));

        ivDown = findViewById(R.id.iv_down);
        ivDown.setOnClickListener(view -> presenter.vote(id, Contract.SCORE_HATE));
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected DetailPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void attach(DetailPresenter presenter) {
        presenter.attach(this);
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void displayImage(Image image) {
        this.image = image;
        setTitle(image.id);
        Picasso.with(this)
                .load(image.url)
                .into(ivFull, new Callback() {
                    @Override
                    public void onSuccess() {
                        applyPalette(((BitmapDrawable) ivFull.getDrawable()).getBitmap());
                    }

                    @Override
                    public void onError() {
                        presenter.onError(new ImageLoadingException(image.id));
                    }
                });
        switch (image.score) {
            case Contract.SCORE_LOVE:
                displayUpvote();
                break;
            case Contract.SCORE_HATE:
                displayDownvote();
                break;
        }
        logViewItem();
    }

    @Override
    public void displayUpvote() {
        setImageViewColor(ivUp, R.color.accent);
    }

    @Override
    public void displayDownvote() {
        setImageViewColor(ivDown, R.color.accent);
    }

    @Override
    public void resetVoteButtons() {
        setImageViewColor(ivUp, R.color.icons);
        setImageViewColor(ivDown, R.color.icons);
    }

    @Override
    public void toastUpvote() {
        toast(R.string.notification_upvoted);
    }

    @Override
    public void toastDownvote() {
        toast(R.string.notification_downvoted);
    }

    public static Intent getIntent(Activity activity, String id) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    private void setImageViewColor(ImageView imageView, @ColorRes int color) {
        imageView.setColorFilter(ContextCompat.getColor(this, color),
                android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void shareText(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }

    private void applyPalette(Bitmap bitmap) {
        Palette.from(bitmap).generate(palette -> {
            int muted = palette.getMutedColor(getResources().getColor(R.color.primary));
            int color = palette.getVibrantColor(muted);
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setBackgroundDrawable(new ColorDrawable(color));
                getWindow().setStatusBarColor(color);
            }
        });
    }

    private void logViewItem() {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_ID, image.id);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);
    }

    public static final class ImageLoadingException extends Exception {

        private final String imageId;

        ImageLoadingException(String imageId) {
            this.imageId = imageId;
        }

        @Override
        public String getMessage() {
            return "Failed to load image " + imageId;
        }
    }

}
