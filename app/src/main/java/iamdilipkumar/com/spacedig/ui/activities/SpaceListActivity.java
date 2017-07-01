package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.adapters.MainListAdapter;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.ui.fragments.AboutFragment;
import iamdilipkumar.com.spacedig.ui.fragments.SearchFragment;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.DialogUtils;

public class SpaceListActivity extends AppCompatActivity implements MainListAdapter.MainListClick {

    @BindView(R.id.rv_main_staggered)
    RecyclerView mMainList;

    List<SimpleItemModel> mListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_list);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                CommonUtils.shareData(this, getString(R.string.share_content));
                break;
            case R.id.action_about:
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

        if (CommonUtils.checkNetworkConnectivity(this)) {
            if (position == 1) {
                startActivity(new Intent(this, FullDetailActivity.class));
                return;
            } else if (position == 4) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, new SearchFragment())
                        .addToBackStack(null).commit();
                return;
            }

            Log.d("selection",""+position);
            Intent intent = new Intent(this, GeneralItemListActivity.class);
            switch(position){
                case 0:
                    intent.putExtra(GeneralItemListActivity.LOAD_API, 0);
                    break;
                case 3:
                    intent.putExtra(GeneralItemListActivity.LOAD_API, 1);
                    break;
                case 5:
                    intent.putExtra(GeneralItemListActivity.LOAD_API, 6);
                    break;
                case 6:
                    intent.putExtra(GeneralItemListActivity.LOAD_API, 5);
                    break;
            }
            startActivity(intent);
        } else {
            DialogUtils.noNetworkPreActionDialog(this);
        }
    }

    @Override
    public void onMainItemInformationClick(int position) {
        DialogUtils.singleButtonDialog(this, mListItems.get(position).getName(),
                mListItems.get(position).getInformation());
    }
}
