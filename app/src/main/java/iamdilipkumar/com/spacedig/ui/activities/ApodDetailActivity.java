package iamdilipkumar.com.spacedig.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.Apod;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.Network.ApiInterface;
import iamdilipkumar.com.spacedig.utils.Network.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 20/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class ApodDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_main_image)
    ImageView mainImage;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_description)
    TextView description;

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    @BindView(R.id.tv_powered)
    TextView poweredBy;

    private static final String TAG = "FullDetailActivity";
    private CompositeDisposable mCompositeDisposable;
    String mExplanation, mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod_detail);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.apod));

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);

        mCompositeDisposable.add(apiInterface.getApod()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiResponse, this::apiError));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_full_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_share:
                if (mExplanation != null) {
                    String shareContent = mTitle +
                            "\n\n" + mExplanation +
                            "\n\n" + getString(R.string.share_content);
                    CommonUtils.shareData(this, shareContent);
                }
                break;
        }

        return true;
    }

    private void apiResponse(Apod apod) {
        loading.setVisibility(View.GONE);
        poweredBy.setVisibility(View.VISIBLE);

        if (apod != null) {

            mTitle = apod.getTitle();
            mExplanation = apod.getExplanation();
            String imageUrl = apod.getUrl();
            String copyright = apod.getCopyright();

            if (imageUrl != null) {
                if (!imageUrl.isEmpty()) {
                    Glide.with(this).load(imageUrl)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.space_dig_main)
                            .crossFade(600)
                            .into(mainImage);
                }
            }

            if (mTitle != null) {
                if (!mTitle.isEmpty()) {
                    title.setText(mTitle);
                }
            }

            if (mExplanation != null) {
                if (!mExplanation.isEmpty()) {
                    description.setText(mExplanation);
                }
            }

            if (copyright != null) {
                if (!copyright.isEmpty()) {
                    copyright = "\n\n" + getString(R.string.copyright) + " " + copyright;
                    description.append(copyright);
                }
            }
        }
    }

    private void apiError(Throwable throwable) {
        loading.setVisibility(View.GONE);
        title.setText(getString(R.string.error_message));
        Log.d(TAG, throwable.getLocalizedMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
