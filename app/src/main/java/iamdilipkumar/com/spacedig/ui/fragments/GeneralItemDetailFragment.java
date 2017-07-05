package iamdilipkumar.com.spacedig.ui.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.MediaOptions;
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

    @OnClick(R.id.tv_full_screen)
    void fullScreenMode() {
        Bundle bundle = new Bundle();

        if (mediaOptions != null) {
            bundle.putString(FullAssetFragment.FULL_VIDEO, mediaOptions.getSmall());
        } else {
            bundle.putString(FullAssetFragment.FULL_IMAGE, simpleItemModel.getImageUrl());
        }

        FullAssetFragment fullAssetFragment = new FullAssetFragment();
        fullAssetFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.full_asset_container, fullAssetFragment)
                .addToBackStack(null)
                .commit();
    }

    SimpleItemModel simpleItemModel;
    MediaOptions mediaOptions;

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

            if (simpleItemModel.getVideoDownloadUrl() != null) {
                if (!simpleItemModel.getVideoDownloadUrl().isEmpty()) {
                    HttpGetRequest getRequest = new HttpGetRequest();
                    getRequest.execute("https://images-assets.nasa.gov/video/NHQ_2017_0523_FY18%20State%20Of%20NASA%20Budget/collection.json");
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_item_detail, container, false);

        ButterKnife.bind(this, view);

        title.setText(simpleItemModel.getName());
        description.setText(simpleItemModel.getInformation());

        return view;
    }

    private class HttpGetRequest extends AsyncTask<String, Void, String> {

        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;
        String medium, original, small;

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String result = null, inputLine;

            try {
                URL myUrl = new URL(url);

                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();

                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();
                connection.disconnect();

                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONArray mediaArray = new JSONArray(result);
                for (int i = 0; i < mediaArray.length(); i++) {
                    String item = mediaArray.getString(i).replace("http://", "https://");
                    if (item.contains("orig.")) {
                        original = item.replace(" ", "%20");
                        Log.d("results", original);
                    } else if (item.contains("medium.")) {
                        medium = item.replace(" ", "%20");
                        Log.d("results", medium);
                    } else if (item.contains("small.")) {
                        small = item.replace(" ", "%20");
                        Log.d("results", small);
                    }
                }

                mediaOptions = new MediaOptions(original, medium, small);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
