package iamdilipkumar.com.spacedig.ui;

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

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.Apod;
import iamdilipkumar.com.spacedig.utils.ApiInterface;
import iamdilipkumar.com.spacedig.utils.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 20/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class FullDetailActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_detail);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.apod));
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
        }

        return true;
    }

    private void apiResponse(Apod apod) {
        loading.setVisibility(View.GONE);
        poweredBy.setVisibility(View.VISIBLE);

        if (apod != null) {

            String mainTitle = apod.getTitle();
            String explanation = apod.getExplanation();
            String imageUrl = apod.getUrl();
            String copyright = apod.getCopyright();

            if (imageUrl != null) {
                if (!imageUrl.isEmpty()) {
                    Picasso.with(this).load(imageUrl).into(mainImage);
                }
            }

            if (mainTitle != null) {
                if (!mainTitle.isEmpty()) {
                    title.setText(mainTitle);
                }
            }

            if (explanation != null) {
                if (!explanation.isEmpty()) {
                    description.setText(explanation);
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
