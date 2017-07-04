package iamdilipkumar.com.spacedig.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.search.Search;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.Network.ApiInterface;
import iamdilipkumar.com.spacedig.utils.Network.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
                loadSearchData(searchText.getText().toString());
            }
        }

        return true;
    }

    private static final String TAG = "SearchFragment";
    private CompositeDisposable mCompositeDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mCompositeDisposable = new CompositeDisposable();
        ButterKnife.bind(this, view);
        return view;
    }

    private void loadSearchData(String searchText) {

        ApiInterface apiInterface = NetworkUtils.buildRetrofitWithoutKey().create(ApiInterface.class);

        mCompositeDisposable.add(apiInterface.getSearchData(searchText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiSearchResponse, this::apiError));
        // Replace .srt with ~orig.mp4
        Toast.makeText(getActivity(), searchText, Toast.LENGTH_SHORT).show();
    }

    private void apiSearchResponse(Search search){
        if(search!=null){
            Log.d(TAG,""+search.getCollection().getItems().size());
        }
    }

    private void apiError(Throwable throwable) {
        Log.d("api error", "error: " + throwable.getLocalizedMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
