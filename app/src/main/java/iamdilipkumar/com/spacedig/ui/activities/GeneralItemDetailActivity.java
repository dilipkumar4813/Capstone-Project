package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.ui.fragments.GeneralItemDetailFragment;

/**
 * An activity representing a single GeneralItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link GeneralItemListActivity}.
 */
public class GeneralItemDetailActivity extends AppCompatActivity {

    public static final String ITEM_DETAILS = "item_detail";
    private SimpleItemModel mSimpleItemModel;
    @BindView(R.id.iv_main_image)
    ImageView mainImage;

    @OnClick(R.id.fab)
    void shareAction() {
        Toast.makeText(this, "Replace with your own detail action", Toast.LENGTH_SHORT).show();
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
            arguments.putParcelable(GeneralItemDetailActivity.ITEM_DETAILS,
                    mSimpleItemModel);
            GeneralItemDetailFragment fragment = new GeneralItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.generalitem_detail_container, fragment)
                    .commit();
        } else {
            mSimpleItemModel = savedInstanceState.getParcelable(ITEM_DETAILS);
        }

        if (mSimpleItemModel != null) {
            actionBar.setTitle(mSimpleItemModel.getName());
            String image = mSimpleItemModel.getImageUrl();

            Glide.with(this).load(image)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.space_dig_main)
                    .into(mainImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, GeneralItemListActivity.class));
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
