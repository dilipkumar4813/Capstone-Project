package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

import iamdilipkumar.com.spacedig.adapters.GeneralListAdapter;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.data.NeoColumns;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.epic.Epic;
import iamdilipkumar.com.spacedig.models.neo.NearEarthObject;
import iamdilipkumar.com.spacedig.models.neo.Neo;
import iamdilipkumar.com.spacedig.models.rover.MarsRover;
import iamdilipkumar.com.spacedig.models.rover.MarsRoverPhoto;
import iamdilipkumar.com.spacedig.ui.fragments.GeneralItemDetailFragment;
import iamdilipkumar.com.spacedig.utils.ApiInterface;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 22/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class GeneralItemListActivity extends AppCompatActivity implements GeneralListAdapter.GridListClick {

    private static final String LIST_ITEMS = "loaded_list";
    public static final String LOAD_API = "api_call";
    List<SimpleItemModel> mGeneralItems;

    @BindView(R.id.generalitem_list)
    RecyclerView mGridList;

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    private CompositeDisposable mCompositeDisposable;
    private boolean mTwoPane;
    int mloadAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalitem_list);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.generalitem_detail_container) != null) {
            mTwoPane = true;
        }

        if (savedInstanceState == null) {
            mGeneralItems = new ArrayList<>();
            mloadAPI = getIntent().getIntExtra(LOAD_API, 0);

            ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);
            switch (mloadAPI) {
                case 0:
                    getSupportActionBar().setTitle(getString(R.string.mars_rover));
                    mCompositeDisposable.add(apiInterface.getRoverPhotos()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiRoverResponse, this::apiError));
                    break;
                case 1:
                    getSupportActionBar().setTitle(getString(R.string.epic));
                    mCompositeDisposable.add(apiInterface.getEpicData("2017-01-01")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiEpicResponse, this::apiError));
                    break;
                case 2:
                    getSupportActionBar().setTitle(getString(R.string.neo));
                    loadNearEarthObjects();
                    /*mCompositeDisposable.add(apiInterface.getNeoData()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiNeoResponse, this::apiError));*/
                    break;
            }
        } else {
            if (savedInstanceState.getParcelableArrayList(LIST_ITEMS) != null) {
                mGeneralItems = savedInstanceState.getParcelableArrayList(LIST_ITEMS);
                GeneralListAdapter rcAdapter = new GeneralListAdapter(this, mGeneralItems);
                mGridList.setAdapter(rcAdapter);
                rcAdapter.notifyDataSetChanged();
            }
        }
    }

    private void loadNearEarthObjects() {
        loading.setVisibility(View.GONE);
        mGeneralItems = CommonUtils.getNeoList(this);
        loadAdapter();
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

    private void apiNeoResponse(Neo neo) {
        loading.setVisibility(View.GONE);
        if (neo.getNearEarthObjects() != null) {
            for (NearEarthObject item : neo.getNearEarthObjects()) {
                mGeneralItems.add(CommonUtils.getNeoModel(item, this));
            }
        }

        loadAdapter();
    }

    private void apiRoverResponse(MarsRover marsRoverPhotos) {
        loading.setVisibility(View.GONE);
        int count = 0;
        for (MarsRoverPhoto item : marsRoverPhotos.getPhotos()) {
            if (count < 100) {
                mGeneralItems.add(CommonUtils.getRoverModel(item, this));
            } else {
                break;
            }
            count++;
        }

        loadAdapter();
    }

    private void apiEpicResponse(List<Epic> epic) {
        loading.setVisibility(View.GONE);
        for (Epic item : epic) {
            mGeneralItems.add(CommonUtils.getEpicModel(item, this));
        }

        loadAdapter();
    }

    private void loadAdapter() {
        if (mGeneralItems.size() > 0) {
            GeneralListAdapter rcAdapter = new GeneralListAdapter(this, mGeneralItems);
            mGridList.setAdapter(rcAdapter);
            rcAdapter.notifyDataSetChanged();
        }
    }

    private void apiError(Throwable throwable) {
        Log.d("api error", "error: " + throwable.getLocalizedMessage());
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onMainItemClick(int position) {
        SimpleItemModel passItem = mGeneralItems.get(position);
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(GeneralItemDetailActivity.ITEM_DETAILS, passItem);
            GeneralItemDetailFragment fragment = new GeneralItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.generalitem_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, GeneralItemDetailActivity.class);
            intent.putExtra(GeneralItemDetailActivity.ITEM_DETAILS, passItem);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(LIST_ITEMS, new ArrayList<>(mGeneralItems));
        outState.putInt(LOAD_API, mloadAPI);
        super.onSaveInstanceState(outState);
    }
}
