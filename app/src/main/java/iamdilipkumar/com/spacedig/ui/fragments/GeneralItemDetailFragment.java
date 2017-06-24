package iamdilipkumar.com.spacedig.ui.fragments;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.ui.activities.GeneralItemDetailActivity;
import iamdilipkumar.com.spacedig.ui.activities.GeneralItemListActivity;

/**
 * A fragment representing a single GeneralItem detail screen.
 * This fragment is either contained in a {@link GeneralItemListActivity}
 * in two-pane mode (on tablets) or a {@link GeneralItemDetailActivity}
 * on handsets.
 */
public class GeneralItemDetailFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_description)
    TextView description;

    SimpleItemModel simpleItemModel;

    public GeneralItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(GeneralItemDetailActivity.ITEM_DETAILS)) {
            simpleItemModel = getArguments().getParcelable(GeneralItemDetailActivity.ITEM_DETAILS);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout
                    = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(simpleItemModel.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.generalitem_detail, container, false);

        ButterKnife.bind(this, view);

        title.setText(simpleItemModel.getName());
        description.setText(simpleItemModel.getInformation());

        return view;
    }
}
