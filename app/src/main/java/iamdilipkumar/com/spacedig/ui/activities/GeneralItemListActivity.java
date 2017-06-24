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

import iamdilipkumar.com.spacedig.adapters.GeneralListAdapter;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.epic.Epic;
import iamdilipkumar.com.spacedig.models.rover.MarsRover;
import iamdilipkumar.com.spacedig.models.rover.MarsRoverPhoto;
import iamdilipkumar.com.spacedig.models.rover.Rover;
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
public class GeneralItemListActivity extends AppCompatActivity implements GeneralListAdapter.GridListClick {

    private static final String TAG = "GeneralItemListActivity";
    public static final String LOAD_API = "api_call";

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

            ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);
            switch (loadData) {
                case 0:
                    mCompositeDisposable.add(apiInterface.getRoverPhotos()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiRoverResponse, this::apiError));
                    break;
                case 1:
                    mCompositeDisposable.add(apiInterface.getEpicData("2017-01-01")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiEpicResponse, this::apiError));
                    break;
                case 2:
                    mCompositeDisposable.add(apiInterface.getEpicData("2017-01-01")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiEpicResponse, this::apiError));
                    break;
            }
        }
    }

    private void apiRoverResponse(MarsRover marsRoverPhotos) {
        int count = 0;
        List<SimpleItemModel> epicItems = new ArrayList<>();
        for (MarsRoverPhoto item : marsRoverPhotos.getPhotos()) {
            if (count < 100) {
                marsRover.add(item);

                String landingDate = "", launchDate = "", status = "";
                Rover rover = item.getRover();
                if (rover != null) {
                    landingDate = getString(R.string.landing_date) + " " + rover.getLandingDate();
                    launchDate = getString(R.string.launch_date) + " " + rover.getLaunchDate();
                    status = getString(R.string.status) + " " + rover.getStatus();
                }
                String fullDescription = landingDate + "\n" + launchDate + "\n" + status;
                SimpleItemModel simpleItemModel = new SimpleItemModel(String.valueOf(item.getId()),
                        item.getCamera().getName(),
                        item.getCamera().getFullName(), item.getImgSrc(), fullDescription, 0);
                epicItems.add(simpleItemModel);
            } else {
                break;
            }
            count++;
        }

        GeneralListAdapter rcAdapter = new GeneralListAdapter(this, epicItems);
        mGridList.setAdapter(rcAdapter);
        rcAdapter.notifyDataSetChanged();
    }

    private void apiEpicResponse(List<Epic> epic) {

        List<SimpleItemModel> epicItems = new ArrayList<>();
        for (Epic item : epic) {
            String image = item.getImage();
            String centroidCoordinates = "";
            String earthDate = getString(R.string.earth_date)
                    + " " + item.getDate();
            String coordinates = getString(R.string.coordinates) + " "
                    + item.getCoords();

            if (item.getCentroidCoordinates() != null) {
                centroidCoordinates = getString(R.string.centroid_coordinates) + " "
                        + item.getCentroidCoordinates().getLat() + ", "
                        + item.getCentroidCoordinates().getLon();
            }

            String description = item.getCaption() + "\n\n" + coordinates + "\n\n"
                    + earthDate + "\n\n" + centroidCoordinates;
            SimpleItemModel simpleItemModel = new SimpleItemModel("", earthDate,
                    centroidCoordinates, image, description, 0);
            epicItems.add(simpleItemModel);
        }
        GeneralListAdapter rcAdapter = new GeneralListAdapter(this, epicItems);
        mGridList.setAdapter(rcAdapter);
        rcAdapter.notifyDataSetChanged();
        Log.d(TAG, "" + epic.size());
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
