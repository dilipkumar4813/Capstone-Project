package iamdilipkumar.com.spacedig.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

/**
 * Created on 04/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class FullAssetFragment extends Fragment {

    @BindView(R.id.iv_full_screen)
    ImageView ivFull;

    public static final String FULL_IMAGE = "fullimage";

    public FullAssetFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_asset, container, false);

        ButterKnife.bind(this,view);

        if(getArguments().containsKey(FULL_IMAGE)){
            String imageUrl = getArguments().getString(FULL_IMAGE);
            Picasso.with(getContext()).load(imageUrl).into(ivFull);
        }

        return view;
    }
}
