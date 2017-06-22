package iamdilipkumar.com.spacedig.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.adapters.RoverListAdapter;
import iamdilipkumar.com.spacedig.models.MarsRover;
import iamdilipkumar.com.spacedig.models.MarsRoverPhoto;
import iamdilipkumar.com.spacedig.utils.ApiInterface;
import iamdilipkumar.com.spacedig.utils.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 21/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class GridListingActivity extends AppCompatActivity implements RoverListAdapter.GridListClick {

    private static final String TAG = "GridListingActivity";

    @BindView(R.id.rv_main_staggered)
    RecyclerView mGridList;

    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_list);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this);

        mGridList.setHasFixedSize(true);

        int listColumns = getResources().getInteger(R.integer.main_list_columns);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, listColumns);
        mGridList.setLayoutManager(gridLayoutManager);

        ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);

        mCompositeDisposable.add(apiInterface.getRoverPhotos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiResponse, this::apiError));
    }

    private void apiResponse(MarsRover marsRoverPhotos) {

        Log.d(TAG, "" + marsRoverPhotos.getPhotos().size());

        List<MarsRoverPhoto> marsRover = new ArrayList<>();
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

    @Override
    public void onMainItemClick(int position) {

    }

    private void apiError(Throwable throwable) {
        Log.d(TAG, throwable.getLocalizedMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
