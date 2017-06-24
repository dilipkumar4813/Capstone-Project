package iamdilipkumar.com.spacedig.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

/**
 * Created on 24/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SearchFragment extends Fragment {

    @BindView(R.id.et_search)
    EditText searchText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this,view);

        return view;
    }
}
