package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

import iamdilipkumar.com.spacedig.adapters.GeneralListAdapter;
import iamdilipkumar.com.spacedig.data.ApplicationPersistence;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.cad.Cad;
import iamdilipkumar.com.spacedig.models.epic.Epic;
import iamdilipkumar.com.spacedig.models.neo.NearEarthObject;
import iamdilipkumar.com.spacedig.models.neo.Neo;
import iamdilipkumar.com.spacedig.models.rover.MarsRover;
import iamdilipkumar.com.spacedig.models.rover.MarsRoverPhoto;
import iamdilipkumar.com.spacedig.models.search.Search;
import iamdilipkumar.com.spacedig.ui.fragments.GeneralDetailFragment;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.DialogUtils;
import iamdilipkumar.com.spacedig.utils.ParsingUtils;
import iamdilipkumar.com.spacedig.utils.Network.ApiInterface;
import iamdilipkumar.com.spacedig.utils.Network.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static iamdilipkumar.com.spacedig.ui.fragments.SearchFragment.SEARCH_TEXT;

/**
 * A general class to access the loading listing for EPIC, CAD, NEO, Mars Rover and Search
 * <p>
 * Created on 22/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class GeneralListActivity extends AppCompatActivity implements GeneralListAdapter.GridListClick {

    private static final String LIST_ITEMS = "loaded_list";
    public static final String LOAD_API = "api_call";
    public static final String LISTING_TITLE = "listing_title";
    List<SimpleItemModel> mGeneralItems;

    @BindView(R.id.generalitem_list)
    RecyclerView mGridList;

    @BindView(R.id.pb_loading)
    ProgressBar loading;

    private CompositeDisposable mCompositeDisposable;
    private boolean mTwoPane;
    private String mListingTitle;
    int mloadAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_list);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.generalitem_detail_container) != null) {
            mTwoPane = true;
        }

        if (savedInstanceState == null) {
            mGeneralItems = new ArrayList<>();
            mloadAPI = getIntent().getIntExtra(LOAD_API, 0);

            if (ApplicationPersistence.getSelectedItem(this) != 0) {
                mloadAPI = ApplicationPersistence.getSelectedItem(this);
            }

            ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);
            switch (mloadAPI) {
                case 0:
                    mListingTitle = getString(R.string.mars_rover);
                    mCompositeDisposable.add(apiInterface.getRoverPhotos()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiRoverResponse, this::apiError));
                    break;
                case 1:
                    mListingTitle = getString(R.string.epic);
                    mCompositeDisposable.add(apiInterface.getEpicData("2017-01-01")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiEpicResponse, this::apiError));
                    break;
                case 4:
                    mListingTitle = getString(R.string.search_results);
                    String searchText = getIntent().getStringExtra(SEARCH_TEXT);

                    ApiInterface apiInterfaceNoKey = NetworkUtils.buildRetrofitWithoutKey()
                            .create(ApiInterface.class);
                    mCompositeDisposable.add(apiInterfaceNoKey.getSearchData(searchText)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::apiSearchResponse, this::apiError));
                    break;
                case 5:
                    mListingTitle = getString(R.string.neo);
                    loadNearEarthObjects();
                    break;
                case 6:
                    mListingTitle = getString(R.string.collision_approach);
                    loadCadObjects();
                    break;
            }
            getSupportActionBar().setTitle(mListingTitle);
        } else {
            getSupportActionBar().setTitle(savedInstanceState.getString(LISTING_TITLE));

            if (savedInstanceState.getParcelableArrayList(LIST_ITEMS) != null) {
                loading.setVisibility(View.GONE);
                mGeneralItems = savedInstanceState.getParcelableArrayList(LIST_ITEMS);
                GeneralListAdapter rcAdapter = new GeneralListAdapter(this, mGeneralItems);
                mGridList.setAdapter(rcAdapter);
                rcAdapter.notifyDataSetChanged();
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.END);
            slide.setDuration(400);
            slide.addTarget(R.id.frameLayout);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setEnterTransition(slide);
        }
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

    /**
     * Method that is called if the rover API has successfully executed
     *
     * @param marsRoverPhotos - Contains the list of photos and its relevant information
     */
    private void apiRoverResponse(MarsRover marsRoverPhotos) {
        loading.setVisibility(View.GONE);
        int count = 0;
        for (MarsRoverPhoto item : marsRoverPhotos.getPhotos()) {
            if (count < 100) {
                mGeneralItems.add(ParsingUtils.getRoverModel(item, this));
            } else {
                break;
            }
            count++;
        }

        loadAdapter();
    }

    /**
     * Method to load the Search results either it being an image or a video
     * Parsing of individual items using ParsingUtils static class
     *
     * @param search - Results of search
     */
    private void apiSearchResponse(Search search) {
        loading.setVisibility(View.GONE);
        if (search != null) {
            mGeneralItems = ParsingUtils.getSearchDetails(this, search);
            loadAdapter();
        }
    }

    /**
     * Method to get parse EPIC data after successful API call
     * Parsing using ParsingUtils
     *
     * @param epic - Earth imagery results
     */
    private void apiEpicResponse(List<Epic> epic) {
        loading.setVisibility(View.GONE);
        for (Epic item : epic) {
            mGeneralItems.add(ParsingUtils.getEpicModel(item, this));
        }

        loadAdapter();
    }

    /**
     * Load NEO results using the content provider
     */
    private void loadNearEarthObjects() {
        loading.setVisibility(View.GONE);
        mGeneralItems = ParsingUtils.getNeoList(this);

        if (mGeneralItems.size() > 0) {
            loadAdapter();
        } else {
            if (CommonUtils.checkNetworkConnectivity(this)) {
                ApiInterface apiNeoInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);
                mCompositeDisposable.add(apiNeoInterface.getNeoData()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::apiNeoResponse, this::apiError));
            } else {
                DialogUtils.noNetworkPreActionDialog(this);
            }
        }
    }

    /**
     * Load CAD results using the content provider
     */
    private void loadCadObjects() {
        loading.setVisibility(View.GONE);
        mGeneralItems = ParsingUtils.getCadContent(this);

        if (mGeneralItems.size() > 0) {
            loadAdapter();
        } else {
            if (CommonUtils.checkNetworkConnectivity(this)) {
                ApiInterface apiCadInterface = NetworkUtils.buildCadRetrofit().create(ApiInterface.class);
                mCompositeDisposable.add(apiCadInterface.getCadData("10LD", "2017-01-01", "dist")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::apiCadResponse, this::apiError));
            } else {
                DialogUtils.noNetworkPreActionDialog(this);
            }
        }
    }

    /**
     * Method to load the adapter once the listing has been populated
     */
    private void loadAdapter() {
        if (mGeneralItems.size() > 0) {
            GeneralListAdapter rcAdapter = new GeneralListAdapter(this, mGeneralItems);
            mGridList.setAdapter(rcAdapter);
            rcAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Method to parse Neo Object into Content provider
     *
     * @param neo - Object received from retrofit response
     */
    private void apiNeoResponse(Neo neo) {
        if (neo.getNearEarthObjects() != null) {

            getContentResolver().delete(NasaProvider.NeoData.CONTENT_URI, null, null);

            for (NearEarthObject item : neo.getNearEarthObjects()) {
                ParsingUtils.getNeoModel(item, this);
            }
        }

        loadNearEarthObjects();
    }

    /**
     * Method to get the CAD data
     *
     * @param cad - list of CAD data
     */
    private void apiCadResponse(Cad cad) {
        if (cad != null) {
            getContentResolver().delete(NasaProvider.CadData.CONTENT_URI, null, null);

            ParsingUtils.getCadDataIntoContentProvider(cad, this);
        }

        loadCadObjects();
    }

    /**
     * Generalized method to extract the error and display the error log
     *
     * @param throwable - used to access the error message
     */
    private void apiError(Throwable throwable) {
        Log.w("api error", "error: " + throwable.getLocalizedMessage());
        loading.setVisibility(View.GONE);

        DialogUtils.singleButtonDialog(GeneralListActivity.this,
                getString(R.string.error),
                getString(R.string.error_message));
    }

    /**
     * Method to handle the click of individual items if its a single screen
     * or a multi screen
     *
     * @param position - item position in the recyclerview
     */
    @Override
    public void onMainItemClick(int position) {
        SimpleItemModel passItem = mGeneralItems.get(position);
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(GeneralDetailActivity.ITEM_DETAILS, passItem);
            GeneralDetailFragment fragment = new GeneralDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.generalitem_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, GeneralDetailActivity.class);
            intent.putExtra(GeneralDetailActivity.ITEM_DETAILS, passItem);
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
        outState.putString(LISTING_TITLE, mListingTitle);
        super.onSaveInstanceState(outState);
    }
}
