package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.adapters.MainListAdapter;
import iamdilipkumar.com.spacedig.data.ApplicationPersistence;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.ui.fragments.AboutFragment;
import iamdilipkumar.com.spacedig.ui.fragments.SearchFragment;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.DialogUtils;

public class SpaceListActivity extends AppCompatActivity implements MainListAdapter.MainListClick {

    @BindView(R.id.rv_main_staggered)
    RecyclerView mMainList;

    private FirebaseAnalytics mFirebaseAnalytics;
    List<SimpleItemModel> mListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_list);

        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setIcon(R.drawable.toolbar_image);


        mMainList.setHasFixedSize(true);

        int listColumns = getResources().getInteger(R.integer.main_list_columns);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(listColumns, 1);
        mMainList.setLayoutManager(staggeredGridLayoutManager);

        List<SimpleItemModel> staggeredList = getListItemData();

        MainListAdapter rcAdapter = new MainListAdapter(this, staggeredList);
        mMainList.setAdapter(rcAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_space_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                logEvent(getString(R.string.analytics_share));
                CommonUtils.shareData(this, getString(R.string.share_content));
                break;
            case R.id.action_about:
                logEvent(getString(R.string.analytics_about));
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, new AboutFragment())
                        .addToBackStack(null).commit();
                break;
        }
        return true;
    }

    private List<SimpleItemModel> getListItemData() {

        TypedArray images = getResources().obtainTypedArray(R.array.main_list_images);
        String[] listNames = getResources().getStringArray(R.array.main_list_names);
        String[] listInformations = getResources().getStringArray(R.array.main_list_information);

        for (int i = 0; i < listNames.length; i++) {
            mListItems.add(new SimpleItemModel(listNames[i], listInformations[i], images.getResourceId(i, -1)));
        }

        images.recycle();

        return mListItems;
    }

    @Override
    public void onMainItemClick(int position) {

        boolean loadActivity = true;
        Intent intent = new Intent(this, GeneralListActivity.class);
        switch (position) {
            case 0:
                logEvent(getString(R.string.analytics_mars));
                if (CommonUtils.checkNetworkConnectivity(this)) {
                    ApplicationPersistence.storeSelectedItem(this, 0);
                    intent.putExtra(GeneralListActivity.LOAD_API, 0);
                } else {
                    DialogUtils.noNetworkPreActionDialog(this);
                }
                break;
            case 1:
                logEvent(getString(R.string.analytics_apod));
                if (CommonUtils.checkNetworkConnectivity(this)) {
                    startActivity(new Intent(this, ApodDetailActivity.class));
                } else {
                    DialogUtils.noNetworkPreActionDialog(this);
                }

                loadActivity = false;
                break;
            case 2:
                logEvent(getString(R.string.analytics_earth_imagery));
                startActivity(new Intent(SpaceListActivity.this, MapsLocationActivity.class));
                loadActivity = false;
                break;
            case 3:
                logEvent(getString(R.string.analytics_epic));
                if (CommonUtils.checkNetworkConnectivity(this)) {
                    ApplicationPersistence.storeSelectedItem(this, 1);
                    intent.putExtra(GeneralListActivity.LOAD_API, 1);
                } else {
                    DialogUtils.noNetworkPreActionDialog(this);
                }
                break;
            case 4:
                logEvent(getString(R.string.analytics_search));
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, new SearchFragment())
                        .addToBackStack(null).commit();

                loadActivity = false;
                break;
            case 5:
                logEvent(getString(R.string.analytics_cad));
                ApplicationPersistence.storeSelectedItem(this, 6);
                intent.putExtra(GeneralListActivity.LOAD_API, 6);
                break;
            case 6:
                logEvent(getString(R.string.analytics_neo));
                ApplicationPersistence.storeSelectedItem(this, 5);
                intent.putExtra(GeneralListActivity.LOAD_API, 5);
                break;
        }

        if (loadActivity) {
            startActivity(intent);
        }
    }

    @Override
    public void onMainItemInformationClick(int position) {
        DialogUtils.singleButtonDialog(this, mListItems.get(position).getName(),
                mListItems.get(position).getInformation());
    }

    private void logEvent(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "MAIN_LISTING");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "card");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
