package iamdilipkumar.com.spacedig.ui;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.adapters.MainListAdapter;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
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
            } else {
                startActivity(new Intent(this, GridListingActivity.class));
            }
        } else {
            DialogUtils.noNetworkPreActionDialog(this);
        }
    }

    @Override
    public void onMainItemInformationClick(int position) {
        DialogUtils.singleButtonDialog(this, mListItems.get(position).getmName(),
                mListItems.get(position).getmInformation());
    }
}
