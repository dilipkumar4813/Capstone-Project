package iamdilipkumar.com.spacedig.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iamdilipkumar.com.spacedig.R;

/**
 * Created on 24/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
