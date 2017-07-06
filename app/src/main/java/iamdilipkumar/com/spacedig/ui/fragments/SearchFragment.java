package iamdilipkumar.com.spacedig.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.ui.activities.GeneralListActivity;
import iamdilipkumar.com.spacedig.utils.CommonUtils;

/**
 * Created on 24/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SearchFragment extends Fragment {

    @BindView(R.id.et_search)
    EditText searchText;

    @OnEditorAction(R.id.et_search)
    boolean searchAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (CommonUtils.checkNetworkConnectivity(getContext())) {
                try {
                    String search = URLEncoder.encode(searchText.getText().toString(), "UTF-8");
                    Intent intent = new Intent(getActivity(), GeneralListActivity.class);
                    intent.putExtra(GeneralListActivity.LOAD_API, 4);
                    intent.putExtra(SEARCH_TEXT, search);
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public static final String SEARCH_TEXT = "searchtext";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);
        return view;
    }
}
