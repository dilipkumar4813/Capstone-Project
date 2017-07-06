package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.ui.fragments.GeneralDetailFragment;
import iamdilipkumar.com.spacedig.utils.CommonUtils;

/**
 * An activity representing a single GeneralItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link GeneralListActivity}.
 */
public class GeneralDetailActivity extends AppCompatActivity {

    public static final String ITEM_DETAILS = "item_detail";
    private SimpleItemModel mSimpleItemModel;

    @OnClick(R.id.fab)
    void shareAction() {
        if (mSimpleItemModel != null) {
            String shareContent = mSimpleItemModel.getName() + "\n\n" +
                    mSimpleItemModel.getInformation() +
                    "\n\n" + getString(R.string.share_content);
            CommonUtils.shareData(this, shareContent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalitem_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            mSimpleItemModel = getIntent().getParcelableExtra(ITEM_DETAILS);

            Bundle arguments = new Bundle();
            arguments.putParcelable(GeneralDetailActivity.ITEM_DETAILS,
                    mSimpleItemModel);
            GeneralDetailFragment fragment = new GeneralDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.generalitem_detail_container, fragment)
                    .commit();
        } else {
            mSimpleItemModel = savedInstanceState.getParcelable(ITEM_DETAILS);
        }

        if (mSimpleItemModel != null) {
            assert actionBar != null;
            actionBar.setTitle(mSimpleItemModel.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, GeneralListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ITEM_DETAILS, mSimpleItemModel);
        super.onSaveInstanceState(outState);
    }
}
