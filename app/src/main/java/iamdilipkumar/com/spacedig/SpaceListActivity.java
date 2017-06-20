package iamdilipkumar.com.spacedig;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.adapters.MainListAdapter;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;

public class SpaceListActivity extends AppCompatActivity implements MainListAdapter.MainListClick {

    @BindView(R.id.rv_main_staggered)
    RecyclerView mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_list);

        ButterKnife.bind(this);

        mMainList.setHasFixedSize(true);

        int listColumns = getResources().getInteger(R.integer.main_list_columns);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(listColumns, 1);
        mMainList.setLayoutManager(staggeredGridLayoutManager);

        List<SimpleItemModel> staggeredList = getListItemData();

        MainListAdapter rcAdapter = new MainListAdapter(this, staggeredList);
        mMainList.setAdapter(rcAdapter);
    }

    private List<SimpleItemModel> getListItemData() {
        List<SimpleItemModel> listItems = new ArrayList<>();

        TypedArray images = getResources().obtainTypedArray(R.array.main_list_images);
        String[] listNames = getResources().getStringArray(R.array.main_list_names);

        for (int i = 0; i < listNames.length; i++) {
            listItems.add(new SimpleItemModel(listNames[i], "", images.getResourceId(i, -1)));
        }

        images.recycle();

        return listItems;
    }

    @Override
    public void onMainItemClick(int position) {

    }

    @Override
    public void onMainItemInformationClick(int position) {
        Toast.makeText(this, "information", Toast.LENGTH_SHORT).show();
    }
}
