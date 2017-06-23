package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

import iamdilipkumar.com.spacedig.adapters.RoverListAdapter;
import iamdilipkumar.com.spacedig.models.rover.MarsRover;
import iamdilipkumar.com.spacedig.models.rover.MarsRoverPhoto;
import iamdilipkumar.com.spacedig.utils.ApiInterface;
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
public class GeneralItemListActivity extends AppCompatActivity implements RoverListAdapter.GridListClick {

    private static final String TAG = "GeneralItemListActivity";
    private static final String LOAD_API = "api_call";

    @BindView(R.id.generalitem_list)
    RecyclerView mGridList;

    private CompositeDisposable mCompositeDisposable;
    private boolean mTwoPane;
    List<MarsRoverPhoto> marsRover = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalitem_list);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.mars_rover));

        if (findViewById(R.id.generalitem_detail_container) != null) {
            mTwoPane = true;
        }

        if (savedInstanceState == null) {
            int loadData = getIntent().getIntExtra(LOAD_API, 0);
        }

        ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);

        mCompositeDisposable.add(apiInterface.getRoverPhotos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiResponse, this::apiError));
    }

    private void apiResponse(MarsRover marsRoverPhotos) {
        Log.d(TAG, "" + marsRoverPhotos.getPhotos().size());
        int count = 0;
        for (MarsRoverPhoto item : marsRoverPhotos.getPhotos()) {
            if (count < 100) {
                marsRover.add(item);
            } else {
                break;
            }
            count++;
        }

        RoverListAdapter rcAdapter = new RoverListAdapter(this, this, marsRover);
        mGridList.setAdapter(rcAdapter);
        rcAdapter.notifyDataSetChanged();
    }

    private void apiError(Throwable throwable) {
        Log.d(TAG, throwable.getLocalizedMessage());
    }

    @Override
    public void onMainItemClick(int position) {
        String id = String.valueOf(marsRover.get(position).getId());
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(GeneralItemDetailFragment.ARG_ITEM_ID, id);
            GeneralItemDetailFragment fragment = new GeneralItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.generalitem_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, GeneralItemDetailActivity.class);
            intent.putExtra(GeneralItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
